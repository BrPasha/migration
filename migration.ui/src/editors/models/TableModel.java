package editors.models;

import java.beans.PropertyChangeListener;

import org.eclipse.gef4.common.properties.IPropertyChangeNotifier;

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
    implements IPropertyChangeNotifier
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
    
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        // TODO Auto-generated method stub

    }

}

