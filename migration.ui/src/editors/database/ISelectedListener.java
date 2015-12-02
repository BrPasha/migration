package editors.database;

/**
 * <pre>
 * Title: ISelectedListener
 * Description: TODO
 * Copyright: Copyright (c) 2015
 * Company: Rocket Software, Inc
 * </pre>
 * @author pgizatullin
 */
public interface ISelectedListener
{
    void select(boolean selected, Object source, Object data);
    
    void highlight(boolean highlighted, Object data);
}

