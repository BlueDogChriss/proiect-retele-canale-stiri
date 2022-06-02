package client;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import common.Settings;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class ClientShell extends Shell {
	private final List list;
	private final Text text;
	private final Client client;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			ClientShell shell = new ClientShell(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public ClientShell(Display display) throws IOException {
		super(display, SWT.SHELL_TRIM);
		setImage(null);
		addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				try {
					client.close();
				}catch(Exception ignored) {
					
				}
			}
		});
		setLayout(new GridLayout(1, false));
	 
	 Label lblNewLabel = new Label(this, SWT.NONE);
	 GridData gdLblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
	 gdLblNewLabel.widthHint = 422;
	 lblNewLabel.setLayoutData(gdLblNewLabel);
	 lblNewLabel.setToolTipText("");
	 lblNewLabel.setAlignment(SWT.CENTER);
	 lblNewLabel.setText("CHANELS");
		
	 list = new List(this, SWT.BORDER);
		GridData gdList = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gdList.widthHint = 419;
		
		list.setLayoutData(gdList);
		
		text = new Text(this, SWT.BORDER);
		
	
		list.add("ALEGETI OPTIUNEA DORITA:");
		list.add("1) LISTA CANALE");
		list.add("2) ADAUGATI CANAL");
		list.add("3) STERGETI CANAL");
		list.add("4) ALEGETI CANALUL LA CARE DORITI SA VA ABONATI");
		list.add("5) ADAUGATI STIRI");
		list.add("6) PARASITI CANALUL DE STIRI");
		
		
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {
					if(e.keyCode==SWT.CR && text.getText().trim().length()>0) {
						client.send(text.getText().trim());
						text.setText("");
					}
				}catch(Exception ignored) {
					
				}
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		createContents();
		
		client=new Client(Settings.HOST,Settings.PORT,mesaj->{

			Display.getDefault().asyncExec(()->{
				list.add(mesaj);
				list.redraw();
			});
		});
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("CANALE - STIRI");
		setSize(450, 700);

	}

	@Override
	protected void checkSubclass() {
		// TODO document why this method is empty
	}

}
