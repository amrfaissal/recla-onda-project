package com.example.reclaadmin;

import java.net.UnknownHostException;

import com.googlecode.mcvaadin.McEvent;
import com.googlecode.mcvaadin.McListener;
import com.googlecode.mcvaadin.helpers.UserMessages;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("serial")
public class RespView extends VerticalLayout {

	ReclaadminApplication __app;
	CustomLayout custom = new CustomLayout("resp");
	MenuBar mainmenu = new MenuBar();
	private Panel centrale = new Panel();
	
	
	/*
	 * Constructor
	 */
	public RespView(ReclaadminApplication application)
	{
		__app = application;
		
		custom.addComponent(mainmenu, "mainmenu");
		custom.addComponent(centrale, "centrale");
		addComponent(custom);
		
		centrale.setIcon(new ThemeResource("icons/actions/wizard.png"));
		centrale.setWidth("635px");
		//centrale.setHeight("620px");
		//centrale.setSizeUndefined();
		centrale.setVisible(false);
		
		//adding items the Main Menu
		MenuBar.MenuItem email = mainmenu.addItem("Email", null);
		email.setIcon(new ThemeResource("icons/actions/message.png"));
		email.addItem("Nouveau Email", new ThemeResource("icons/actions/mail_new.png"), mailCommand);
		email.addSeparator();
		email.addItem("Consulter", new ThemeResource("icons/actions/mail_get.png"), null);
		email.addSeparator();
		email.addItem("Liste des contacts", new ThemeResource("icons/actions/mail_replyall.png"), getMailingList);
		
		
		MenuBar.MenuItem analyse = mainmenu.addItem("Actions Entreprise", null);
		analyse.setIcon(new ThemeResource("icons/actions/wizard.png"));
		analyse.addItem("Tableau des actions", new ThemeResource("icons/actions/misc.png"), getAnalyse);
		
		MenuBar.MenuItem dConsole = mainmenu.addItem("Konsole", decisiaKonsole);
		dConsole.setIcon(new ThemeResource("icons/actions/openterm.png"));
		
		MenuBar.MenuItem print = mainmenu.addItem("Imprimer", null);
		print.setIcon(new ThemeResource("icons/actions/fileprint.png"));
		
		MenuBar.MenuItem help = mainmenu.addItem("Help", null);
		help.setIcon(new ThemeResource("icons/actions/help.png"));
		
		MenuBar.MenuItem logout = mainmenu.addItem("Logout", _logout);
		logout.setIcon(new ThemeResource("icons/actions/exit.png"));
		
		setMargin(true);
		
	}
	

	private Command _logout = new Command() {
		@Override
		public void menuSelected(MenuItem selectedItem) {
			// custom user message__confirmation dialog
			UserMessages um = new UserMessages(__app.getMainWindow());
			um.confirm("Confirmation",
					"Vous voulez vraiment quitter la session ?", "Oui", "Non",
					new McListener() {

						@Override
						public void exec(McEvent e) throws Exception {
							// message confirmed
							if (e.isConfirmed()) {
								try {
									__app.getViewManager().switchScreen(
											LoginScreen.class.getName(),
											new LoginScreen(__app));
								} catch (UnknownHostException ex) {
									ex.printStackTrace();
								}
							}
						}
					});
		}
	};

	private Command mailCommand = new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) 
			{
				centrale.setVisible(true);
				switchView(centrale, new AdminMail(__app, centrale));
			}    
	};
	
	private Command getMailingList = new Command() {
		@Override
		public void menuSelected(MenuItem selectedItem) 
		{
			centrale.setVisible(true);
			switchView(centrale, new MailingList(__app, centrale));
		}    

	};
	
	
	private Command getAnalyse = new Command(){

		@Override
		public void menuSelected(MenuItem selectedItem) {
			centrale.setVisible(true);
			switchView(centrale, new TableauActions(__app, centrale));
		}	
	};
	
	private Command decisiaKonsole = new Command() {

		@Override
		public void menuSelected(MenuItem selectedItem) {
			centrale.setVisible(true);
			switchView(centrale, new Konsole(centrale));
		}
	};
	
	
	/*
	 * a tool to switch between panel views
	 */
	public void switchView(Panel window , Layout newView)
	{
		window.setContent(newView);
	}
	

}
