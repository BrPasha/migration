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
public class FileModel
    implements IPropertyChangeNotifier
{
    
    private String name;
    private FieldModel[] fields;
    
    public FileModel(String name, FieldModel[] fields){
        this.name = name;
        this.fields = fields;
    }
    
    public String getName(){
        return name;
    }
    
    public FieldModel[] getFields(){
        return fields;
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

