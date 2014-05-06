package happy.coding.io.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A <code>TextTableModel</code> for <code>java.util.Map</code>.
 * 
 * @author Daniel Orr
 * 
 */
public class MapBasedTableModel extends TextTableModel {

	private static final long serialVersionUID = 1L;
	private List<String> columnNames;
	
	private List<Map<String, Object>> maps;

	public MapBasedTableModel(List<Map<String, Object>> maps) {
		this.columnNames = new ArrayList<>(maps.get(0).keySet());
		this.maps = maps;
	}

	@Override
	public int getRowCount() {
		return maps.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public String getColumnName(int column) {
		return columnNames.get(column);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Map<String, Object> m = maps.get(rowIndex);
		String columnName = columnNames.get(columnIndex);
		return m.get(columnName);
	}

	@Override
	public boolean allowNumberingAt(int row) {
		return false;
	}

	@Override
	public boolean addSeparatorAt(int row) {
		return false;
	}

}
