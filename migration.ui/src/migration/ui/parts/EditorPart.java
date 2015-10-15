package migration.ui.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class EditorPart {
    private TreeViewer m_viewer;
	
	public EditorPart() 
	{
	}
	
	@PostConstruct
	public void createControls(Composite parent) 
	{
		m_viewer = new TreeViewer(parent, SWT.BORDER);
	}
	
	@Focus
	public void setFocus() 
	{
		
	}
}
