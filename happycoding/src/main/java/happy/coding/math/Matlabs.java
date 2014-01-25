package happy.coding.math;

import happy.coding.io.Logs;
import happy.coding.io.Params;

import java.util.Arrays;

import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;

/**
 * Matlab Interfact classes to call Matlab functions, especially useful for data
 * analysis and visualization
 * 
 * @author guoguibing
 * 
 */
public class Matlabs {

	private static MatlabProxyFactoryOptions options = null;
	private static MatlabProxyFactory factory = null;
	private static MatlabProxy proxy = null;

	private Matlabs() {
	}

	public static MatlabProxy getProxy() throws Exception {
		if (options == null)
			options = new MatlabProxyFactoryOptions.Builder().setUsePreviouslyControlledSession(true).setHidden(false)
					.build();

		if (factory == null)
			factory = new MatlabProxyFactory(options);

		proxy = factory.getProxy();

		return proxy;
	}

	public static void plot(double[] x, double[] y) throws Exception {
		plot(x, y, false, new Params());
	}

	public static void plot(double[] x, double[] y, boolean hold) throws Exception {
		plot(x, y, hold, new Params());
	}

	/**
	 * Plot 2D images using Matlab
	 * 
	 * <ul>
	 * <li><a href="http://www.mathworks.com/help/matlab/ref/linespec.html">Line
	 * Specification</a></li>
	 * </ul>
	 * 
	 * @param ops
	 *            the parameter options
	 */
	public static void plot(double[] x, double[] y, boolean hold, Params ops) throws Exception {
		proxy.setVariable("x", x);
		proxy.setVariable("y", y);

		if (hold)
			proxy.eval("hold on");

		proxy.eval("plot(x, y, '" + ops.getParam("linespec", "--bs") + "', 'LineWidth', "
				+ ops.getParam("LineWidth", "1.5") + ")");
		proxy.eval("title('" + ops.getParam("title", "Y ~ X") + "')");
		proxy.eval("xlabel('" + ops.getParam("xlabel", "X") + "')");
		proxy.eval("ylabel('" + ops.getParam("title", "Y") + "')");

		for (Object gen : ops.getParams("legend"))
			proxy.eval("legend('" + gen.toString() + "')");
	}

	public static void main(String[] args) throws Exception {
		MatlabProxy proxy = Matlabs.getProxy();

		proxy.eval("disp('hello world')");
		proxy.feval("disp", "Hello world again!");

		Object[] inmem = proxy.returningFeval("inmem", 3);
		Logs.debug("Java classes loaded: ");
		Logs.debug(Arrays.toString((String[]) inmem[2]));

		proxy.setVariable("a", 5);
		proxy.eval("a = a + 6;");
		Logs.debug("Result: {}", ((double[]) proxy.getVariable("a"))[0]);

		Matlabs.plot(new double[] { 1, 2, 3 }, new double[] { 4, 5, 6 });
		Matlabs.plot(new double[] { 4, 3, 1 }, new double[] { 4, 5, 6 }, true, new Params().addParam("linespec", ":mo"));

		proxy.disconnect();
	}
}
