package editors.models;

/**
 * <pre>
 * Title: TableModel
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class TableModel
{
    
    private String name;
    private ColumnModel[] columns;
    
    public TableModel(String name, ColumnModel[] columns){
        this.name = name;
        this.columns = columns;
    }
    
    public String getName(){
        return name;
    }
    
    public ColumnModel[] getColumns(){
        return columns;
    }
}

