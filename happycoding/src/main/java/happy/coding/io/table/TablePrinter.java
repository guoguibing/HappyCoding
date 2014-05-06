package happy.coding.io.table;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * The original work is created bjy Daniel Orr, and the projected is hosted in
 * <a href="https://code.google.com/p/j-text-utils/">Google code:
 * j-text-utils</a><br/>
 * 
 * @author Daniel Orr
 * 
 * @author guoguibing (modified)
 * 
 */
public class TablePrinter {

	protected TableModel tableModel;
	protected List<SeparatorPolicy> separatorPolicies = new ArrayList<SeparatorPolicy>();

	protected boolean addRowNumbering;

	protected RowSorter<?> rowSorter;

	protected boolean headless;

	public TablePrinter(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public TablePrinter(TableModel tableModel, boolean addNumbering) {
		this.tableModel = tableModel;
		this.addRowNumbering = addNumbering;
	}

	public TablePrinter(String[] columnNames, Object[][] data) {
		this.tableModel = new DefaultTableModel(data, columnNames);
	}

	public TableModel getTableModel() {
		return tableModel;
	}

	public void setAddRowNumbering(boolean addNumbering) {
		this.addRowNumbering = addNumbering;
	}

	public void addSeparatorPolicy(SeparatorPolicy separatorPolicy) {
		separatorPolicies.add(separatorPolicy);
		separatorPolicy.setTableModel(tableModel);
	}

	public void setSort(int column) {
		setSort(column, SortOrder.ASCENDING);
	}

	public void setSort(int column, SortOrder sortOrder) {
		rowSorter = new TableRowSorter<TableModel>(this.tableModel);
		List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		// sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(column, sortOrder));
		rowSorter.setSortKeys(sortKeys);
	}

	public void printTable() {
		printTable(System.out, 0);
	}

	public void printTable(PrintStream ps, int indent) {
		TextTableRenderer renderer = new TextTableRenderer(this);
		renderer.render(ps, indent);
	}

	protected Object getValueAt(int row, int column) {
		int rowIndex = row;
		if (rowSorter != null) {
			rowIndex = rowSorter.convertRowIndexToModel(row);
		}
		return tableModel.getValueAt(rowIndex, column);
	}

	protected boolean hasSeparatorAt(int row) {
		for (SeparatorPolicy separatorPolicy : separatorPolicies) {
			if (separatorPolicy.hasSeparatorAt(row)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String[] columnNames = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };

		Object[][] data = { { "Kathy", "Smith", "Snowboarding", 5, false }, { "John", "Doe", "Rowing", 3, true },
				{ "Sue", "Black", "Knitting", 2, false }, { "Jane", "White", "Speed reading", 20, true },
				{ "Joe", "Brown", "Pool", 10, false } };

		TablePrinter printer = new TablePrinter(columnNames, data);
		// this adds the numbering on the left      
		printer.setAddRowNumbering(true);
		// sort by the second column                              
		printer.setSort(1);
		printer.printTable();
	}

}
