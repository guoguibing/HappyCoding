package happy.coding.io;

import happy.coding.io.net.Gmailer;
import happy.coding.io.net.URLReader;
import happy.coding.io.net.WebPages;
import happy.coding.system.Dates;
import happy.coding.system.Systems;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class FileIO {
	// 1G in bytes or units
	public static final long ONE_KB = 1024;
	public static final long ONE_K = 1000;

	// 1M in bytes or units
	public static final long ONE_MB = ONE_KB * ONE_KB;
	public static final long ONE_M = ONE_K * ONE_K;

	// 1K in bytes or units
	public static final long ONE_GB = ONE_KB * ONE_MB;
	public static final long ONE_G = ONE_K * ONE_M;

	public final static String dirResource = makeDirectory(new String[] { "src", "main", "resources" });
	public final static String desktop = Systems.getDesktop();

	/* comma without consideration in the quotas, such as "boys, girls" */
	public final static String comma = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

	/**
	 * interface for converting an entry of a map to string
	 * 
	 * @param <K>
	 *            key type
	 * @param <V>
	 *            value type
	 */
	public interface MapWriter<K, V> {
		String processEntry(K key, V val);
	}

	/**
	 * Transform an input object with Type K to an output object with type T
	 * 
	 * @param <K>
	 *            type of input object
	 * @param <T>
	 *            type of output object
	 */
	public interface Converter<K, T> {
		T transform(K in) throws Exception;
	}

	/**
	 * Should not be instanced
	 */
	private FileIO() {
	}

	/**
	 * Returns a human-readable version of the file size, where the input
	 * represents a specific number of bytes.
	 * 
	 * @param size
	 *            the number of bytes
	 * @return a human-readable display value (includes units)
	 */
	public static String formatBytes(long size) {
		String display;

		if (size / ONE_GB > 0) {
			display = String.format("%.2f", (size + 0.0) / ONE_GB) + " GB";
		} else if (size / ONE_MB > 0) {
			display = String.format("%.2f", (size + 0.0) / ONE_MB) + " MB";
		} else if (size / ONE_KB > 0) {
			display = String.format("%.2f", (size + 0.0) / ONE_KB) + " KB";
		} else {
			display = String.valueOf(size) + " bytes";
		}
		return display;
	}

	/**
	 * Returns a human-readable version of the file size.
	 * 
	 * @param size
	 *            the size of a file in units (not in bytes)
	 * @return a human-readable display value
	 */
	public static String formatSize(long size) {
		String display;

		if (size / ONE_G > 0) {
			display = String.format("%.2f", (size + 0.0) / ONE_G) + " G";
		} else if (size / ONE_M > 0) {
			display = String.format("%.2f", (size + 0.0) / ONE_M) + " M";
		} else if (size / ONE_K > 0) {
			display = String.format("%.2f", (size + 0.0) / ONE_K) + " K";
		} else {
			display = String.valueOf(size);
		}
		return display;
	}

	/**
	 * Get resource path, supporting file and url io path
	 * 
	 * @return
	 */
	public static String getResource(String filePath) {
		if (FileIO.exist(filePath))
			return filePath;

		String path = "./src/main/resources/" + filePath;
		if (FileIO.exist(path))
			return path;
		
		URL is = Class.class.getResource(filePath);
		if (is != null)
			return is.getFile();

		is = Class.class.getResource(path);
		if (is != null)
			return is.getFile();

		return null;
	}

	public static BufferedReader getReader(String path) throws FileNotFoundException {
		return new BufferedReader(new FileReader(new File(getResource(path))));
	}

	public static BufferedWriter getWriter(String path) throws Exception {
		return new BufferedWriter(new FileWriter(new File(getResource(path))));
	}

	/**
	 * @return the name of current folder
	 */
	public static String getCurrentFolder() {
		return Paths.get("").toAbsolutePath().getFileName().toString();
	}

	/**
	 * @return the path to current folder
	 */
	public static String getCurrentPath() {
		return Paths.get("").toAbsolutePath().toString();
	}

	/**
	 * Make directory path: make sure the path is ended with file separator
	 * 
	 * @param dirPath
	 * @return corrected directory path with file separator in the end
	 */
	public static String makeDirPath(String dirPath) {
		switch (Systems.getOs()) {
		case Windows:
			dirPath = dirPath.replace('/', '\\');
			break;
		default:
			dirPath = dirPath.replace('\\', '/');
			break;
		}

		if (!dirPath.endsWith(Systems.FILE_SEPARATOR))
			dirPath += Systems.FILE_SEPARATOR;

		return dirPath;
	}

	/**
	 * make directory path using the names of directories
	 * 
	 * @param dirs
	 * @return
	 * @throws Exception
	 */
	public static String makeDirPath(String... dirs) {
		String dirPath = "";
		for (String dir : dirs)
			dirPath += makeDirPath(dir);

		return dirPath;
	}

	/**
	 * Make directory if it does not exist
	 * 
	 * @param dirPath
	 * @return Directory path with file separator in the end
	 */
	public static String makeDirectory(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists())
			dir.mkdirs();

		return makeDirPath(dir.getPath());
	}

	/**
	 * Construct directory and return directory path
	 * 
	 * @param dirs
	 * @return constructed directory path
	 */
	public static String makeDirectory(String... dirs) {
		String dirPath = makeDirPath(dirs);
		return makeDirectory(dirPath);
	}

	/**
	 * Write a string into a file
	 * 
	 * @param filePath
	 *            : the name of file to be written
	 * @param content
	 *            : the content of a string to be written
	 * @throws Exception
	 */
	public static void writeString(String filePath, String content) throws Exception {
		writeString(filePath, content, false);
	}

	public static void writeString(String filePath, String content, boolean append) throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath), append));
		if (content.endsWith("\n"))
			bw.write(content);
		else
			bw.write(content + "\n");
		bw.close();
	}

	public static <T> void writeList(String filePath, Collection<T> objs) throws Exception {
		writeList(filePath, objs, null, false);
	}

	public synchronized static <T> void writeListSyn(String filePath, List<T> objs) throws Exception {
		writeList(filePath, objs, null, false);
	}

	/**
	 * Write contents in Collection<T> to a file with the help of a writer
	 * helper
	 * 
	 * @param <T>
	 *            type of Objects in the collection
	 * 
	 */
	public static <T> void writeList(String filePath, Collection<T> ts, Converter<T, String> lw, boolean append)
			throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath), append));
		StringBuilder contents = new StringBuilder();
		int count = 0;
		for (T t : ts) {

			contents.append(lw != null ? lw.transform(t) : t);
			contents.append("\n");
			count++;

			if (count >= 1000) {
				bw.write(contents.toString());
				count = 0;
				contents = new StringBuilder();
			}
		}
		if (contents.capacity() > 0)
			bw.write(contents.toString());
		bw.close();
	}

	public static <T> void writeVector(String filePath, List<T> objs) throws Exception {
		writeVector(filePath, objs, null, false);
	}

	public static <T> void writeVector(String filePath, List<T> ts, Converter<T, String> wh, boolean append)
			throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath), append));
		int i = 0;
		StringBuilder sb = new StringBuilder();
		for (T t : ts) {
			sb.append(wh.transform(t));
			if (++i < ts.size())
				sb.append(", ");
		}
		bw.write(sb.toString() + "\n");
		bw.close();
	}

	/**
	 * copy result file (default: "results.txt") to destination fold (default:
	 * "./Results"), and then notify me by email
	 * 
	 * 
	 * @param algo
	 *            the algorithm or method executed
	 * 
	 * @param mailTo
	 *            the email address of receiver
	 * @param isNotify
	 *            whether to notify me by Gmail
	 * 
	 * @throws Exception
	 */
	public static void notifyMe(String algo, String mailTo, boolean isNotify) throws Exception {

		String destPath = FileIO.makeDirectory("Results");
		String dest = destPath + algo + "@" + Dates.now() + ".txt";
		FileIO.copyFile("results.txt", dest);

		if (isNotify) {
			Gmailer notifier = new Gmailer();
			notifier.getProps().setProperty("mail.to", mailTo);
			notifier.getProps().setProperty("mail.subject",
					FileIO.getCurrentFolder() + "." + algo + "@" + Systems.getIP());
			notifier.send("Program [" + algo + "] has been finished !", dest);
		}
	}

	public static void notifyMe(String algo, boolean isNotify) throws Exception {
		notifyMe(algo, "gguo1@e.ntu.edu.sg", isNotify);
	}

	/**
	 * Use line("key,value") format to write a map to a file
	 * 
	 * @param filePath
	 * @param objs
	 * @throws Exception
	 */
	public static <T, E> void writeMap(String filePath, Map<T, E> objs) throws Exception {
		writeMap(filePath, objs, false);
	}

	public static <T, E> void writeMap(String filePath, Map<T, E> map, boolean append) throws Exception {
		writeMap(filePath, map, null, append);
	}

	public static <T, E> void writeMap(String filePath, Map<T, E> map, MapWriter<T, E> mw, boolean append)
			throws Exception {
		String content = Strings.toString(map, mw);

		writeString(filePath, content, append);
	}

	/**
	 * Read the content of a file, if keywords are specified, then only lines
	 * with these keywords will be read
	 * 
	 * @param filePath
	 *            the file to be read
	 * @param keywords
	 *            the keywords of lines to be read
	 * @return the content of a file as string
	 * @throws Exception
	 */
	public static String readAsString(String filePath, String... keywords) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			if (keywords != null && keywords.length > 0) {
				for (String keyword : keywords) {
					if (line.contains(keyword)) {
						sb.append(line + "\r\n");
						break;
					}
				}
			} else
				sb.append(line + "\r\n");
		}

		br.close();
		return sb.toString();
	}

	/**
	 * Read String from file at specified line numbers, e.g. read two lines at
	 * line position 10, 100, starting from line 1. Note that line numbers must
	 * be ordered from min to max; hence before invoke this method, use ordering
	 * method first
	 * 
	 * @param filePath
	 * @param lines
	 * @return
	 * @throws Exception
	 */
	public static String readAsString(String filePath, int... lines) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		StringBuilder sb = new StringBuilder();
		String line = null;
		int count = 0;
		int num = 0;
		while ((line = br.readLine()) != null) {
			count++;
			if (count == lines[num]) {
				num++;
				sb.append(line + "\r\n");
			}
			if (num >= lines.length)
				break;
		}

		br.close();
		return sb.toString();
	}

	public static String readAsString(String path) throws Exception {
		if (path.startsWith("http://") || path.contains("www."))
			return URLReader.read(path);
		else
			return readAsString(path, new String[] {});
	}

	/**
	 * Read the content of a file and return it as a List<String>
	 * 
	 * @param filePath
	 *            : the file to be read
	 * @return the content of a file in java.util.List<String>
	 * @throws Exception
	 */
	public static List<String> readAsList(String filePath) throws FileNotFoundException, Exception {
		return readAsList(filePath, null);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> readAsList(String filePath, Converter<String, T> rh) throws FileNotFoundException,
			Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		List<T> contents = new ArrayList<>();
		T t = null;
		String line = null;
		while ((line = br.readLine()) != null) {
			if (rh == null)
				t = (T) line;
			else
				t = rh.transform(line);
			if (t != null)
				contents.add(t);
		}

		br.close();
		return contents;
	}

	public static Set<String> readAsSet(String filePath) throws FileNotFoundException, Exception {
		return readAsSet(filePath, null);
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> readAsSet(String filePath, Converter<String, T> rh) throws FileNotFoundException,
			Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		Set<T> contents = new HashSet<>();
		String line = null;
		T t = null;
		while ((line = br.readLine()) != null) {
			if (rh == null)
				t = (T) line;
			else
				t = rh.transform(line);
			if (t != null)
				contents.add(t);
		}

		br.close();
		return contents;
	}

	public static Map<String, String> readAsMap(String filePath) throws FileNotFoundException, Exception {
		return readAsMap(filePath, ",");
	}

	public static Map<String, String> readAsMap(String filePath, String seperator) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		Map<String, String> contents = new HashMap<>();
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] data = line.split(seperator);
			if (data.length > 1)
				contents.put(data[0], data[1]);
		}

		br.close();
		return contents;
	}

	@SuppressWarnings("unchecked")
	public static <T, E> Map<T, E> readAsMap(String filePath, Converter<String, Object[]> rh) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		Map<T, E> contents = new HashMap<>();
		String line = null;
		while ((line = br.readLine()) != null) {
			Object[] obs = rh.transform(line);
			contents.put((T) obs[0], (E) obs[1]);
		}

		br.close();
		return contents;
	}

	/**
	 * read a map in the form of Map<Integer, Double>
	 * 
	 * @param filePath
	 * @return Map<Integer, Double>
	 * @throws Exception
	 */
	public static Map<String, Double> readAsIDMap(String filePath) throws Exception {
		return readAsIDMap(filePath, ",");
	}

	/**
	 * read a map in the form of Map<Integer, Double>
	 * 
	 * @param filePath
	 * @return Map<Integer, Double>
	 * @throws Exception
	 */
	public static Map<String, Double> readAsIDMap(String filePath, String sep) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		Map<String, Double> contents = new HashMap<>();
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] data = line.split(sep);
			if (data.length > 1)
				contents.put(data[0], new Double(data[1]));
		}

		br.close();
		return contents;
	}

	public static void writeObj2Xml(String filePath, Object obj) throws Exception {
		FileOutputStream fos = new FileOutputStream(filePath);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		fos.close();
	}

	public static Object readObjByXml(String filePath) throws Exception {
		FileInputStream fis = new FileInputStream(filePath);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		ois.close();
		fis.close();
		return obj;
	}

	/**
	 * Rename files in a folder by replacing keywords
	 * 
	 * @param dirPath
	 *            the directory of files
	 * @param regex
	 *            the old string needed to be replaced, supporting regular
	 *            expression
	 * @param replacement
	 *            the new string used to replace old string
	 * @throws Exception
	 */
	public static void renameFiles(String dirPath, String regex, String replacement) throws Exception {
		File dir = new File(dirPath);
		if (!dir.isDirectory())
			throw new Exception(dirPath + " is not a directory");
		File[] files = dir.listFiles();
		if (files != null && files.length > 0) {
			for (File file : files) {
				renameFile(file, regex, replacement);
			}
		}
	}

	public static void renameFile(File file, String regex, String replacement) {
		String filename = file.getName();
		filename = filename.replaceAll(regex, replacement);

		String path = makeDirPath(file.getPath());
		file.renameTo(new File(path + filename));
	}

	public static void copyFile(String source, String target) throws Exception {
		copyFile(source, target, true);
	}

	public static void copyFile(String source, String target, boolean override) throws Exception {
		copyFile(new File(source), new File(target), override);
	}

	public static void copyFile(File source, File target) throws Exception {
		copyFile(source, target, true);
	}

	public static void copyFile(File source, File target, boolean override) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(source));
		BufferedWriter bw = new BufferedWriter(new FileWriter(target, !override));
		String line = null;
		while ((line = br.readLine()) != null)
			bw.write(line + "\r\n");

		br.close();
		bw.close();
	}

	public static void deleteFile(String source) throws Exception {
		new File(source).delete();
	}

	public static void deleteDirectory(String dirPath) throws Exception {
		deleteDirectory(new File(dirPath));
	}

	public static void deleteDirectory(File dir) throws Exception {

		cleanDirectory(dir);
		dir.delete();
	}

	public static void cleanDirectory(String dirPath) throws Exception {
		cleanDirectory(new File(dirPath));
	}

	public static void cleanDirectory(File dir) throws Exception {
		if (!dir.exists())
			return;
		if (!dir.isDirectory())
			throw new Exception("The path '" + dir.getPath() + "' is not a directory. ");

		File[] fs = dir.listFiles();
		for (File f : fs) {
			if (f.isDirectory())
				deleteDirectory(f.getPath());
			else
				f.delete();
		}
	}

	public static void moveFile(String source, String target) throws Exception {
		copyFile(source, target);

		deleteFile(source);
	}

	public static void moveDirectory(String sourceDir, String targetDir) throws Exception {
		copyDirectory(sourceDir, targetDir);

		deleteDirectory(sourceDir);
	}

	public static void copyDirectory(String sourceDir, String targetDir) throws Exception {
		copyDirectory(sourceDir, targetDir, true);
	}

	public static void copyDirectory(String sourceDir, String targetDir, boolean override) throws Exception {
		File sDir = new File(sourceDir);
		File tDir = new File(targetDir);

		if (sDir.isDirectory()) {
			if (!tDir.exists())
				tDir.mkdirs();

			File[] files = sDir.listFiles();

			for (File f : files) {
				if (f.isDirectory()) {
					copyDirectory(f.getPath(), tDir + Systems.FILE_SEPARATOR + f.getName() + Systems.FILE_SEPARATOR);
				} else {
					copyFile(f, new File(tDir.getPath() + Systems.FILE_SEPARATOR + f.getName()), override);
				}
			}
		}
	}

	public static void empty(String filePath) throws Exception {
		File file = new File(filePath);
		if (file.exists())
			file.delete();
		file.createNewFile();
	}

	public static boolean exist(String filePath) {
		return new File(filePath).exists();
	}

	@Test
	public void example() throws Exception {
		FileIO.writeString(FileIO.desktop + "example.html", FileIO.readAsString(WebPages.ChinaMil));
	}
}
