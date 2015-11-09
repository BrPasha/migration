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
public class FileModel
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

}

