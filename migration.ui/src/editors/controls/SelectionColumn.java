package editors.controls;

/**
 * <pre>
 * Title: SelectionColumn
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class SelectionColumn
{
    private String m_column;
    private TableNode m_table;
    
    public SelectionColumn(String column, TableNode table){
        m_column = column;
        m_table = table;
    }

    public String getColumn()
    {
        return m_column;
    }

    public TableNode getTable()
    {
        return m_table;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof SelectionColumn)){
            return false;
        }
        return ((SelectionColumn)obj).getColumn().equals(m_column) &&
               ((SelectionColumn)obj).getTable().getName().equals(m_table.getName());
    }
}

