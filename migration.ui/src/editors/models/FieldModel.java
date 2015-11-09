package editors.models;

/**
 * <pre>
 * Title: ColumnModel
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public class FieldModel
{
    private String name;
    private boolean isSingleValue;
    private String table;
    
    public FieldModel(String name, boolean isSingleValue, String table){
        this.name = name;
        this.isSingleValue = isSingleValue;
        this.table = table;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String value){
       name = value;
    }
    
    public String getTable(){
        return table;
    }
    
    public boolean getIsSingleValue(){
        return isSingleValue;
    }
    
    public void setIsSingleValue(boolean value){
        isSingleValue = value;
    }
}

