package com.example.reclaadmin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import server.DQSPServer;
import server.DQSPServerI;

import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.addon.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.query.TableQuery;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.data.Property;

@SuppressWarnings("serial")
public class TableauActions extends VerticalLayout{
	
	SimpleDateFormat formatter;
	Table table;
	Button edit;
	Button save;
	Button send;
	ComboBox airports;
	HorizontalLayout hlayout = new HorizontalLayout();
	ReclaadminApplication __app;
	DQSPServer _server = new DQSPServerI();
	
	JDBCConnectionPool pool;
	SQLContainer container;
	TableQuery tableQuery;
	
	
	
	public TableauActions(ReclaadminApplication application, Panel panel)
	{
		__app = application;
		
		panel.setSizeUndefined();
		hlayout.setVisible(false);
		
		formatter = new SimpleDateFormat("d/MM/yyyy");
		table = new Table("Tableau d'actions Entreprise - "+formatter.format(new java.util.Date()));
		table.setVisible(false);
		
		edit = new Button("Editer");
		edit.setIcon(new ThemeResource("icons/actions/edit.png"));
		send = new Button("Envoyer");
		send.setIcon(new ThemeResource("icons/actions/mail_send.png"));
		save = new Button("Enregistrer");
		save.setIcon(new ThemeResource("icons/actions/filesave.png"));
		
		airports = new ComboBox();
		airports.setIcon(new ThemeResource("icons/actions/identity.png"));
		airports.setImmediate(true);
		airports.setInputPrompt("Aéroport Concerné");
		for(String s : _server.listOfAirports()){
			airports.addItem(s);
		}
		
		airports.addListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				table.setWidth("100%");
				table.setContainerDataSource(getActionsContainer());
				table.setPageLength(0);
				table.setFooterVisible(true);
				table.setVisible(true);
				hlayout.setVisible(true);
				
			}
		});
			
		addComponent(airports);
		setComponentAlignment(airports, "right");
		
		table.setSelectable(true);
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(true);
		
		setMargin(true);
		//setSpacing(true);
		
		addComponent(table);
		
		hlayout.setSpacing(true);
		hlayout.setMargin(true);
		
		hlayout.addComponent(edit);
		hlayout.addComponent(save);
		
		//adding listener to buttons
		edit.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				edit.setEnabled(false);
				table.setEditable(!table.isEditable());
				table.setWriteThrough(true);
			}
		});
		
		save.addListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				edit.setEnabled(true);
				table.setEditable(!table.isEditable());
				table.commit();
				
				try {
					container.commit();
				} catch (UnsupportedOperationException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		send.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				__app.getMainWindow().showNotification("Message délivré au DQSP");
			}
		});
		
		hlayout.addComponent(send);
		addComponent(hlayout);
		setComponentAlignment(hlayout, "right");
	}
	
	
	/*
	 * @return a container with items
	 */
	public SQLContainer getActionsContainer() {
		
		try {
			pool = new SimpleJDBCConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pl", "root", "0");
			tableQuery = new TableQuery("myActions",pool);
			container = new SQLContainer(tableQuery);
		} catch (SQLException e) {   
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return container;
	}

}