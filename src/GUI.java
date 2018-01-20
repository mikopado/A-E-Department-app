
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;

import java.util.Enumeration;

import java.util.List;
import java.util.Set;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.border.EtchedBorder;

import java.awt.Component;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;


public class GUI extends JFrame {

	private JPanel mainPanel;
	private JTextField fnameTxt;
	private JLabel lblLastName;
	private JTextField lnameTxt;
	private JLabel lblCity;
	private JTextField cityTxt;
	private JLabel lblAdd;
	private JTextField addressTxt;
	private JLabel lblNationality;
	private JTextField nationTxt;
	private JLabel lblDateOfBirth;
	private JLabel lblNewLabel;
	private JLabel lblIndivdualHelathId;
	private JTextField ihiTxt;
	
	private String fname;
	private String lname;
	private String address;
	private String city;
	private String phoneNo;
	private String nationality;
	private LocalDate dob;
	private int ihi;
	
	
	private String description;	
	
	private IRepository<Patient> patients;
	private IRepository<Transaction> transactions;
	private IPriorityQueue<Node, Integer> queue;
	
	private JTextField fnameNurseTxt;
	private JTextField lnameNurseTxt;
	private JTextField addressNurseTxt;
	private JTextField cityNurseTxt;
	private JTextField nationNurseTxt;
	private JTextField ihiNurseTxt;
	private JTextField dobNurseTxt;
	private JTextField fNameDocTxt;
	private JTextField lnameDocTxt;
	private JTextField addrDocTxt;
	private JTextField ihiDocTxt;
	private JTextField cityDocTxt;
	private JTextField nationDocTxt;
	private JTable table;
	private JTextField idHistTxt;
	private JTable tableHist;
	private JTextField searchTxt;
	private JTextField fnameEditTxt;
	private JTextField lnameEditTxt;
	private JTextField addressEditTxt;
	private JTextField cityEdiTxt;
	private JTextField nationEditTxt;
	
	private JPanel recepPanel;
	private JPanel nursePanel;
	private JPanel doctorPane;
	private JPanel searchPanel;
	private JPanel waitingList;
	private JPanel patientHistPanel;
	private JTextField priorityDocTxt;
	
	private JButton btnReceptionist;
	private JButton btnNurses;
	private JButton btnDoctors;
	private JButton btnWaitingList;
	private JButton btnPatientHistory;
	private JButton btnSearchPatient;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch(SQLException ex) {//Handle database errors such as no table has been created
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Database connection", JOptionPane.ERROR_MESSAGE);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public GUI() throws ParseException, SQLException {
		setResizable(false);
		setTitle("A&E Department");
		
		patients = new BizLogic<Patient>(new OrmMapper<Patient>(new SQLiteDb("A&E_department"), Patient.class));
		transactions = new BizLogic<Transaction>(new OrmMapper<Transaction>(new SQLiteDb("A&E_department"), Transaction.class));
		queue = new DoublyLinkedList();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 696, 559);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		// Formatters for data and phone number fields
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter df = new DateFormatter(format);  		
		MaskFormatter mf = new MaskFormatter("### #######");
		
		//TOOLBAR
		JToolBar toolBar = new JToolBar();
		toolBar.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		toolBar.setBounds(0, 0, 690, 29);
		mainPanel.add(toolBar);
		
		
		JSeparator separator_5 = new JSeparator();
		toolBar.add(separator_5);
		separator_5.setEnabled(false);
		separator_5.setMaximumSize(new Dimension(30, 0));
		separator_5.setOrientation(SwingConstants.VERTICAL);
		
		btnReceptionist = new JButton("Receptionist");		
		btnReceptionist.setFocusPainted(false);		
		btnReceptionist.setHorizontalAlignment(SwingConstants.TRAILING);
		toolBar.add(btnReceptionist);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setMaximumSize(new Dimension(30, 0));
		toolBar.add(separator);
		
		btnNurses = new JButton("Nurses");		
		btnNurses.setFocusPainted(false);
		btnNurses.setHorizontalAlignment(SwingConstants.TRAILING);
		toolBar.add(btnNurses);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setMaximumSize(new Dimension(30, 0));
		toolBar.add(separator_1);
		
		btnDoctors = new JButton("Doctors");
		btnDoctors.setFocusPainted(false);
		btnDoctors.setHorizontalAlignment(SwingConstants.TRAILING);
		toolBar.add(btnDoctors);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setMaximumSize(new Dimension(30, 0));
		toolBar.add(separator_2);
		
		btnWaitingList = new JButton("Waiting List");
		btnWaitingList.setFocusPainted(false);
		btnWaitingList.setHorizontalAlignment(SwingConstants.TRAILING);
		toolBar.add(btnWaitingList);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setMaximumSize(new Dimension(30, 0));
		toolBar.add(separator_3);
		
		btnPatientHistory = new JButton("Patient History");
		btnPatientHistory.setFocusPainted(false);
		btnPatientHistory.setHorizontalAlignment(SwingConstants.TRAILING);
		toolBar.add(btnPatientHistory);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setMaximumSize(new Dimension(30, 0));
		toolBar.add(separator_4);
		
		btnSearchPatient = new JButton("Search Patient");
		btnSearchPatient.setFocusPainted(false);
		btnSearchPatient.setHorizontalAlignment(SwingConstants.TRAILING);
		toolBar.add(btnSearchPatient);
		
		showToolbarButtonClicked(btnReceptionist);
		
		///END TOOLBAR
		
		//SEARCH PANEL
		
		searchPanel = new JPanel();
		searchPanel.setBounds(0, 28, 690, 491);
		mainPanel.add(searchPanel);
		searchPanel.setLayout(null);
		
		JLabel lblPatientIhi = new JLabel("Patient IHI");
		lblPatientIhi.setBounds(198, 12, 86, 20);
		searchPanel.add(lblPatientIhi);
		searchPanel.setVisible(false);
		
		searchTxt = new JTextField();
		searchTxt.setBounds(274, 12, 186, 20);
		searchPanel.add(searchTxt);
		searchTxt.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		
		btnSearch.setBounds(484, 11, 89, 23);
		searchPanel.add(btnSearch);
		
		JLabel lblFirstName_1 = new JLabel("First Name");
		lblFirstName_1.setBounds(178, 80, 86, 20);
		searchPanel.add(lblFirstName_1);
		
		JLabel lblLastName_1 = new JLabel("Last Name");
		lblLastName_1.setBounds(178, 130, 86, 20);
		searchPanel.add(lblLastName_1);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(178, 180, 86, 20);
		searchPanel.add(lblAddress);
		
		JLabel lblCity_1 = new JLabel("City");
		lblCity_1.setBounds(178, 230, 86, 20);
		searchPanel.add(lblCity_1);
		
		JLabel lblNationality_1 = new JLabel("Nationality");
		lblNationality_1.setBounds(178, 280, 86, 20);
		searchPanel.add(lblNationality_1);
		
		JLabel lblDateOfBirth_1 = new JLabel("Date of Birth");
		lblDateOfBirth_1.setBounds(178, 330, 86, 20);
		searchPanel.add(lblDateOfBirth_1);
		
		JLabel lblPhoneNumber_1 = new JLabel("Phone Number");
		lblPhoneNumber_1.setBounds(178, 380, 86, 20);
		searchPanel.add(lblPhoneNumber_1);
		
		fnameEditTxt = new JTextField();
		fnameEditTxt.setBounds(274, 80, 186, 20);
		searchPanel.add(fnameEditTxt);
		fnameEditTxt.setColumns(10);
		
		lnameEditTxt = new JTextField();
		lnameEditTxt.setBounds(274, 130, 186, 20);
		searchPanel.add(lnameEditTxt);
		lnameEditTxt.setColumns(10);
		
		addressEditTxt = new JTextField();
		addressEditTxt.setBounds(274, 180, 186, 20);
		searchPanel.add(addressEditTxt);
		addressEditTxt.setColumns(10);
		
		cityEdiTxt = new JTextField();
		cityEdiTxt.setBounds(274, 230, 186, 20);
		searchPanel.add(cityEdiTxt);
		cityEdiTxt.setColumns(10);
		
		nationEditTxt = new JTextField();
		nationEditTxt.setBounds(274, 280, 186, 20);
		searchPanel.add(nationEditTxt);
		nationEditTxt.setColumns(10);
		
		JFormattedTextField dobEditTxt = new JFormattedTextField(df);
		dobEditTxt.setBounds(274, 330, 186, 20);
		searchPanel.add(dobEditTxt);
		
		JFormattedTextField phoneEditTxt = new JFormattedTextField(mf);
		phoneEditTxt.setBounds(274, 380, 186, 20);
		searchPanel.add(phoneEditTxt);
		
		JButton btnUpdatePatientData = new JButton("Update Patient data");
		
		btnUpdatePatientData.setBounds(178, 430, 186, 23);
		searchPanel.add(btnUpdatePatientData);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(0, 43, 690, 2);
		searchPanel.add(separator_6);
		
		JButton btnAddToWaiting = new JButton("Add to waiting list");
		
		btnAddToWaiting.setBounds(388, 430, 186, 23);
		searchPanel.add(btnAddToWaiting);
		
		//PATIENT HISTORY PANEL
		
		patientHistPanel = new JPanel();
		patientHistPanel.setBounds(0, 28, 690, 491);
		mainPanel.add(patientHistPanel);
		patientHistPanel.setVisible(false);
		
		JLabel lblNewLabel_2 = new JLabel("Patient IHI");
		lblNewLabel_2.setBounds(164, 8, 89, 20);
		patientHistPanel.add(lblNewLabel_2);
		
		idHistTxt = new JTextField();
		idHistTxt.setBounds(250, 8, 156, 20);
		patientHistPanel.add(idHistTxt);
		idHistTxt.setColumns(10);
		
		JButton btnShowRecords = new JButton("Show records");
		
		btnShowRecords.setBounds(433, 7, 121, 23);
		patientHistPanel.add(btnShowRecords);
		
		DefaultTableModel modelHist = new DefaultTableModel();			
		modelHist.addColumn("Description");
		modelHist.addColumn("Timestamp");
		
		tableHist = new JTable(modelHist);		
		tableHist.setEnabled(false);
		
		tableHist.setBounds(0, 39, 690, 452);
		tableHist.setPreferredSize(new Dimension(660, 480));
		JScrollPane scrollHist = new JScrollPane(tableHist);
		scrollHist.setViewportView(tableHist);
		scrollHist.setPreferredSize(new Dimension(680, 480));
		scrollHist.setMinimumSize(new Dimension(200, 200));
		scrollHist.setMaximumSize(new Dimension(2000, 2000));
		tableHist.setFillsViewportHeight(true);
		tableHist.setRowHeight(20);	
		patientHistPanel.add(scrollHist);
		
		//WAITING LIST PANEL
		
		waitingList = new JPanel();
		waitingList.setBounds(0, 28, 690, 492);
		mainPanel.add(waitingList);
		waitingList.setVisible(false);
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Patient Ihi");
		model.addColumn("First Name");
		model.addColumn("Last Name");
		model.addColumn("Date of Birth");
		model.addColumn("Priority");
		table = new JTable(model);
		table.setEnabled(false);
		table.setPreferredSize(new Dimension(660, 480));		
		table.setMinimumSize(new Dimension(200, 200));
		table.setMaximumSize(new Dimension(2000, 2000));		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(680, 480));
		table.setFillsViewportHeight(true);
		table.setRowHeight(20);
		
		waitingList.add(scroll);
		
		//DOCTOR PANEL
		
		doctorPane = new JPanel();
		doctorPane.setBounds(0, 28, 680, 492);
		mainPanel.add(doctorPane);
		doctorPane.setLayout(null);
		doctorPane.setVisible(false);
		
		JLabel label_6 = new JLabel("First Name");
		label_6.setAlignmentX(1.0f);
		label_6.setBounds(125, 62, 130, 20);
		doctorPane.add(label_6);
		
		fNameDocTxt = new JTextField();
		fNameDocTxt.setEditable(false);
		fNameDocTxt.setColumns(10);
		fNameDocTxt.setBackground(Color.WHITE);
		fNameDocTxt.setBounds(255, 62, 196, 20);
		doctorPane.add(fNameDocTxt);
		
		JLabel priorityLbl = new JLabel("Priority");
		priorityLbl.setBounds(500, 62, 130, 20);
		doctorPane.add(priorityLbl);
		
		priorityDocTxt = new JTextField();
		priorityDocTxt.setEditable(false);
		priorityDocTxt.setBackground(Color.WHITE);
		priorityDocTxt.setBounds(550, 62, 40, 20);
		priorityDocTxt.setColumns(10);
		doctorPane.add(priorityDocTxt);
		priorityDocTxt.setColumns(10);
		
		JLabel label_9 = new JLabel("Last Name");
		label_9.setAlignmentX(1.0f);
		label_9.setBounds(125, 103, 130, 20);
		doctorPane.add(label_9);
		
		lnameDocTxt = new JTextField();
		lnameDocTxt.setEditable(false);
		lnameDocTxt.setColumns(10);
		lnameDocTxt.setBackground(Color.WHITE);
		lnameDocTxt.setBounds(255, 103, 196, 20);
		doctorPane.add(lnameDocTxt);
		
		JLabel label_10 = new JLabel("Address");
		label_10.setAlignmentX(1.0f);
		label_10.setBounds(125, 144, 130, 20);
		doctorPane.add(label_10);
		
		addrDocTxt = new JTextField();
		addrDocTxt.setEditable(false);
		addrDocTxt.setColumns(10);
		addrDocTxt.setBackground(Color.WHITE);
		addrDocTxt.setBounds(255, 144, 196, 20);
		doctorPane.add(addrDocTxt);
		
		JLabel label_11 = new JLabel("City");
		label_11.setAlignmentX(1.0f);
		label_11.setBounds(125, 185, 130, 20);
		doctorPane.add(label_11);
		
		ihiDocTxt = new JTextField();
		ihiDocTxt.setEditable(false);
		ihiDocTxt.setColumns(10);
		ihiDocTxt.setBackground(Color.WHITE);
		ihiDocTxt.setBounds(255, 21, 196, 20);
		doctorPane.add(ihiDocTxt);
		
		JLabel label_12 = new JLabel("Nationality");
		label_12.setAlignmentX(1.0f);
		label_12.setBounds(125, 226, 130, 20);
		doctorPane.add(label_12);
		
		cityDocTxt = new JTextField();
		cityDocTxt.setEditable(false);
		cityDocTxt.setColumns(10);
		cityDocTxt.setBackground(Color.WHITE);
		cityDocTxt.setBounds(255, 185, 196, 20);
		doctorPane.add(cityDocTxt);
		
		JLabel label_13 = new JLabel("Date of Birth");
		label_13.setAlignmentX(1.0f);
		label_13.setBounds(125, 267, 130, 20);
		doctorPane.add(label_13);
		
		JLabel label_14 = new JLabel("Individual Health ID");
		label_14.setAlignmentX(1.0f);
		label_14.setBounds(125, 21, 130, 20);
		doctorPane.add(label_14);
		
		nationDocTxt = new JTextField();
		nationDocTxt.setEditable(false);
		nationDocTxt.setColumns(10);
		nationDocTxt.setBackground(Color.WHITE);
		nationDocTxt.setBounds(255, 226, 196, 20);
		doctorPane.add(nationDocTxt);
		
		JTextArea treatmentTxt = new JTextArea();
		treatmentTxt.setWrapStyleWord(true);
		treatmentTxt.setRows(4);
		treatmentTxt.setLineWrap(true);
		treatmentTxt.setColumns(50);
		treatmentTxt.setBorder(new LineBorder(Color.LIGHT_GRAY));
		treatmentTxt.setBounds(155, 351, 402, 74);
		doctorPane.add(treatmentTxt);
		
		JLabel lblTreatmentDescription = new JLabel("Treatment Description");
		lblTreatmentDescription.setAlignmentX(1.0f);
		lblTreatmentDescription.setBounds(10, 377, 130, 20);
		doctorPane.add(lblTreatmentDescription);
		
		JButton terminateBtn = new JButton("Terminate visit");
		
		terminateBtn.setBounds(255, 444, 183, 23);
		doctorPane.add(terminateBtn);
		
		JFormattedTextField phoneDocTxt = new JFormattedTextField(mf);
		phoneDocTxt.setEditable(false);
		phoneDocTxt.setBackground(Color.WHITE);
		phoneDocTxt.setBounds(255, 308, 196, 20);
		doctorPane.add(phoneDocTxt);
		
		JFormattedTextField dobDocTxt = new JFormattedTextField(df);
		dobDocTxt.setEditable(false);
		dobDocTxt.setBackground(Color.WHITE);
		dobDocTxt.setBounds(255, 267, 196, 20);
		doctorPane.add(dobDocTxt);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(125, 308, 130, 20);
		doctorPane.add(lblPhoneNumber);
		
		//NURSE PANEL
		
		nursePanel = new JPanel();
		nursePanel.setVisible(false);
		nursePanel.setBounds(0, 28, 680, 492);
		mainPanel.add(nursePanel);
		nursePanel.setLayout(null);	
		nursePanel.setVisible(false);
		
		
		JLabel label = new JLabel("First Name");
		label.setBounds(128, 62, 130, 20);
		label.setAlignmentX(1.0f);
		nursePanel.add(label);
		
		fnameNurseTxt = new JTextField();
		fnameNurseTxt.setEditable(false);
		fnameNurseTxt.setBackground(Color.WHITE);
		fnameNurseTxt.setBounds(252, 62, 196, 20);
		fnameNurseTxt.setColumns(10);
		nursePanel.add(fnameNurseTxt);
		
		JLabel label_1 = new JLabel("Last Name");
		label_1.setBounds(128, 103, 130, 20);
		label_1.setAlignmentX(1.0f);
		nursePanel.add(label_1);
		
		lnameNurseTxt = new JTextField();
		lnameNurseTxt.setEditable(false);
		lnameNurseTxt.setBackground(Color.WHITE);
		lnameNurseTxt.setBounds(252, 103, 196, 20);
		lnameNurseTxt.setColumns(10);
		nursePanel.add(lnameNurseTxt);
		
		JLabel label_2 = new JLabel("Address");
		label_2.setBounds(128, 144, 130, 20);
		label_2.setAlignmentX(1.0f);
		nursePanel.add(label_2);
		
		addressNurseTxt = new JTextField();
		addressNurseTxt.setEditable(false);
		addressNurseTxt.setBackground(Color.WHITE);
		addressNurseTxt.setBounds(252, 144, 196, 20);
		addressNurseTxt.setColumns(10);
		nursePanel.add(addressNurseTxt);
		
		JLabel label_3 = new JLabel("City");
		label_3.setBounds(128, 185, 130, 20);
		label_3.setAlignmentX(1.0f);
		nursePanel.add(label_3);
		
		cityNurseTxt = new JTextField();
		cityNurseTxt.setEditable(false);
		cityNurseTxt.setBackground(Color.WHITE);
		cityNurseTxt.setBounds(252, 185, 196, 20);
		cityNurseTxt.setColumns(10);
		nursePanel.add(cityNurseTxt);
		
		JLabel label_4 = new JLabel("Nationality");
		label_4.setBounds(128, 226, 130, 20);
		label_4.setAlignmentX(1.0f);
		nursePanel.add(label_4);
		
		nationNurseTxt = new JTextField();
		nationNurseTxt.setEditable(false);
		nationNurseTxt.setBackground(Color.WHITE);
		nationNurseTxt.setBounds(253, 226, 196, 20);
		nationNurseTxt.setColumns(10);
		nursePanel.add(nationNurseTxt);
		
		JLabel label_5 = new JLabel("Date of Birth");
		label_5.setBounds(128, 267, 130, 20);
		label_5.setAlignmentX(1.0f);
		nursePanel.add(label_5);
		
		JLabel label_7 = new JLabel("Individual Health ID");
		label_7.setBounds(128, 21, 130, 20);
		label_7.setAlignmentX(1.0f);
		nursePanel.add(label_7);
		
		ihiNurseTxt = new JTextField();
		ihiNurseTxt.setDisabledTextColor(Color.LIGHT_GRAY);
		ihiNurseTxt.setEditable(false);
		ihiNurseTxt.setBackground(Color.WHITE);
		ihiNurseTxt.setBounds(252, 21, 196, 20);
		ihiNurseTxt.setColumns(10);
		nursePanel.add(ihiNurseTxt);
		
		JTextArea descriptNurseTxt = new JTextArea();
		descriptNurseTxt.setBounds(253, 308, 402, 74);
		descriptNurseTxt.setWrapStyleWord(true);
		descriptNurseTxt.setRows(4);
		descriptNurseTxt.setLineWrap(true);
		descriptNurseTxt.setColumns(50);
		descriptNurseTxt.setBorder(new LineBorder(Color.LIGHT_GRAY));
		nursePanel.add(descriptNurseTxt);
		
		JLabel label_8 = new JLabel("Comments");
		label_8.setBounds(128, 335, 130, 20);
		label_8.setAlignmentX(1.0f);
		nursePanel.add(label_8);
		
		JButton btnSavePriority = new JButton("Save");
		
		
		btnSavePriority.setBounds(253, 458, 183, 23);
		nursePanel.add(btnSavePriority);
		
		JRadioButton rdbtn1 = new JRadioButton("1");
		rdbtn1.setBounds(25, 417, 40, 23);
		nursePanel.add(rdbtn1);
		
		JLabel lblSetPriority = new JLabel("Set Priority");
		lblSetPriority.setBounds(263, 393, 130, 20);
		nursePanel.add(lblSetPriority);
		
		JRadioButton rdbtn2 = new JRadioButton("2");
		rdbtn2.setBounds(90, 417, 40, 23);
		nursePanel.add(rdbtn2);
		
		JRadioButton rdbtn3 = new JRadioButton("3");
		rdbtn3.setBounds(155, 417, 40, 23);
		nursePanel.add(rdbtn3);
		
		JRadioButton rdbtn4 = new JRadioButton("4");
		rdbtn4.setBounds(220, 417, 40, 23);
		nursePanel.add(rdbtn4);
		
		JRadioButton rdbtn5 = new JRadioButton("5");
		rdbtn5.setBounds(285, 417, 40, 23);
		nursePanel.add(rdbtn5);
		
		JRadioButton rdbtn6 = new JRadioButton("6");
		rdbtn6.setBounds(350, 417, 40, 23);
		nursePanel.add(rdbtn6);
		
		JRadioButton rdbtn7 = new JRadioButton("7");
		rdbtn7.setBounds(415, 417, 40, 23);
		nursePanel.add(rdbtn7);
		
		JRadioButton rdbtn8 = new JRadioButton("8");
		rdbtn8.setBounds(480, 417, 40, 23);
		nursePanel.add(rdbtn8);
		
		JRadioButton rdbtn9 = new JRadioButton("9");
		rdbtn9.setBounds(545, 417, 40, 23);
		nursePanel.add(rdbtn9);
		
		JRadioButton rdbtn10 = new JRadioButton("10");
		rdbtn10.setBounds(610, 417, 40, 23);
		nursePanel.add(rdbtn10);
		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rdbtn1);
		btnGroup.add(rdbtn2);
		btnGroup.add(rdbtn3);
		btnGroup.add(rdbtn4);
		btnGroup.add(rdbtn5);
		btnGroup.add(rdbtn6);
		btnGroup.add(rdbtn7);
		btnGroup.add(rdbtn8);
		btnGroup.add(rdbtn9);
		btnGroup.add(rdbtn10);
		
		dobNurseTxt = new JTextField();
		dobNurseTxt.setEditable(false);
		dobNurseTxt.setBackground(Color.WHITE);
		dobNurseTxt.setBounds(253, 267, 196, 20);
		nursePanel.add(dobNurseTxt);
		dobNurseTxt.setColumns(10);
		
		//RECEPTIONIST PANEL
		
		recepPanel = new JPanel();
		recepPanel.setBounds(0, 28, 680, 492);
		mainPanel.add(recepPanel);
		recepPanel.setPreferredSize(new Dimension(60, 60));
		recepPanel.setLayout(null);		
		
		
		JLabel lblFirstName = new JLabel("First Name");		
		lblFirstName.setBounds(115, 62, 132, 20);
		recepPanel.add(lblFirstName);
		
		fnameTxt = new JTextField();
		fnameTxt.setBounds(247, 62, 202, 20);
		recepPanel.add(fnameTxt);
		fnameTxt.setColumns(10);
		
		lblLastName = new JLabel("Last Name");	
		lblLastName.setBounds(115, 103, 132, 20);
		recepPanel.add(lblLastName);
		
		lnameTxt = new JTextField();
		lnameTxt.setBounds(247, 103, 202, 20);
		recepPanel.add(lnameTxt);
		lnameTxt.setColumns(10);
		
		lblCity = new JLabel("City");
		lblCity.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblCity.setBounds(115, 185, 132, 20);
		recepPanel.add(lblCity);
		
		cityTxt = new JTextField();
		cityTxt.setBounds(247, 185, 202, 20);
		recepPanel.add(cityTxt);
		cityTxt.setColumns(10);
		
		lblAdd = new JLabel("Address");
		lblAdd.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblAdd.setBounds(115, 144, 132, 20);		
		recepPanel.add(lblAdd);
		
		addressTxt = new JTextField();
		addressTxt.setBounds(247, 144, 202, 20);
		recepPanel.add(addressTxt);
		addressTxt.setColumns(10);
		
		lblNationality = new JLabel("Nationality");
		lblNationality.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNationality.setBounds(115, 226, 132, 20);
		recepPanel.add(lblNationality);
		
		nationTxt = new JTextField();
		nationTxt.setBounds(247, 226, 202, 20);
		recepPanel.add(nationTxt);
		nationTxt.setColumns(10);
		
		lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblDateOfBirth.setBounds(115, 267, 132, 20);
		recepPanel.add(lblDateOfBirth);
		
		lblNewLabel = new JLabel("Phone Number");
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNewLabel.setBounds(115, 308, 132, 20);
		recepPanel.add(lblNewLabel);
		
		lblIndivdualHelathId = new JLabel("Individual Health ID");
		lblIndivdualHelathId.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblIndivdualHelathId.setBounds(115, 21, 132, 20);
		recepPanel.add(lblIndivdualHelathId);
		
		ihiTxt = new JTextField();
		ihiTxt.setBounds(247, 21, 202, 20);
		recepPanel.add(ihiTxt);
		ihiTxt.setColumns(10);
		ihiTxt.requestFocus();
		
		JFormattedTextField dobTxt = new JFormattedTextField(df);
		dobTxt.setBounds(247, 267, 202, 20);
		recepPanel.add(dobTxt);
		
		JTextArea descripTxt = new JTextArea();
		descripTxt.setBounds(247, 349, 402, 74);
		recepPanel.add(descripTxt);
		descripTxt.setBorder(new LineBorder(Color.LIGHT_GRAY));
		descripTxt.setWrapStyleWord(true);
		descripTxt.setLineWrap(true);
		descripTxt.setColumns(50);
		descripTxt.setRows(4);
		
		JLabel lblComments = new JLabel("Comments");
		lblComments.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblComments.setBounds(115, 376, 132, 20);
		recepPanel.add(lblComments);
		JFormattedTextField phoneTxt = new JFormattedTextField(mf);
		phoneTxt.setBounds(247, 308, 202, 20);
		recepPanel.add(phoneTxt);
		
		
		JButton btnSavePatient = new JButton("Save");
		btnSavePatient.setBounds(247, 444, 132, 23);
		recepPanel.add(btnSavePatient);
		
		JLabel lblNewLabel_1 = new JLabel("yyyy-mm-dd");
		lblNewLabel_1.setBounds(462, 267, 87, 20);
		recepPanel.add(lblNewLabel_1);
		
//####################################################################################################
//									BUTTONS EVENT HANDLERS
//####################################################################################################
		
		btnSavePatient.addActionListener(new ActionListener() {
			// Save new patient or update existing one 
			public void actionPerformed(ActionEvent e) {
				
				String ihiStr = ihiTxt.getText().trim();
				String dobStr = dobTxt.getText();
				
				// Text fields validation.
				//If the PatientID(IHI) or the Date of Birth are not in the correct format, a message dialog pops up to inform user
				if(dobStr.isEmpty()) {
					
					JOptionPane.showMessageDialog(null, "" + lblDateOfBirth.getText() + " must be filled in!", "Validation error", JOptionPane.ERROR_MESSAGE);
					dobTxt.requestFocus();
					
				}else {
					
					fname = fnameTxt.getText();
					lname = lnameTxt.getText();
					address = addressTxt.getText();
					city = cityTxt.getText();
					nationality = nationTxt.getText();					
					phoneNo = phoneTxt.getText();
					dob = LocalDate.parse(dobStr);
					description = descripTxt.getText();
					
					try {// Try catch to handle the number format exception that may occur if user inputs letters and no digits. It's also handled the
						// empty text fields as the IHI must exist as it is the primary key for database
						if(ihiStr.isEmpty()) {
							throw new NumberFormatException();
						}
						
						ihi = Integer.parseInt(ihiStr);
						Patient p = new Patient(ihi, fname, lname, address, city, nationality, dob, phoneNo);
						Transaction t = new Transaction(description, LocalDateTime.now(), ihi);
						
						if(patients.contains(ihi)) {
							// If the PatientID is already stored into database, ask the user if wants to update existing patient
							int ans = JOptionPane.showConfirmDialog(null, "Patient already exists. Would you like to update it?", "Edit Patient", JOptionPane.YES_NO_OPTION);
							
							if(ans == JOptionPane.YES_OPTION) {// If user decides to update patient data. Store new transaction and add patient to the waiting list
								
								if(patients.edit(p)) {
									
									transactions.add(t);
									queue.enqueue(ihi);
									clearTextFields(recepPanel);
									JOptionPane.showMessageDialog(null, "Data patient updated successfully!", "Update data patient", JOptionPane.INFORMATION_MESSAGE);
									
								}else {
									JOptionPane.showMessageDialog(null, "Data patient cannot be updated! Something went wrong", "Update Data Patient", JOptionPane.ERROR_MESSAGE);
								}
								
							}
							
						}else {
							// Save new patient into database and add new patient to the waiting list
							int ans = JOptionPane.showConfirmDialog(null, "Would you like to save new patient?", "Save Patient", JOptionPane.YES_NO_OPTION);
							if(ans == JOptionPane.YES_OPTION) {
								if(patients.add(p)) {
									transactions.add(t);
									queue.enqueue(ihi);
									clearTextFields(recepPanel);
									JOptionPane.showMessageDialog(null, "Data patient saved successfully!", "Save data patient", JOptionPane.INFORMATION_MESSAGE);
								}else {
									JOptionPane.showMessageDialog(null, "Data patient cannot be stored! Something went wrong", "Update Data Patient", JOptionPane.ERROR_MESSAGE);
								}
								
							}
						}
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "" + lblIndivdualHelathId.getText() + " must be a number and cannot be empty!", "Validation error", JOptionPane.ERROR_MESSAGE);
						ihiTxt.setText("");
						
					}
					
					ihiTxt.requestFocus();
					
					
				}
				
				
			}
		});
		
		
		btnSavePriority.addActionListener(new ActionListener() {
			// Set priority to the patient to be visited by doctor
			public void actionPerformed(ActionEvent e) {
				
				if(ihiNurseTxt.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Patient data missing. Cannot save!", "Validation error", JOptionPane.ERROR_MESSAGE);
					
				}else {
					// Only retrieve Patient ID and priority to reorder the waiting list based on the priority
					int patientId = Integer.parseInt(ihiNurseTxt.getText());					
					int priority = Integer.parseInt(getPriorityFromRadioButton(btnGroup));
					int ans = JOptionPane.YES_OPTION;
					
					if(priority == 1) {// Just if the user sets priority equal to 1 on purpose or by mistake
						ans = JOptionPane.showConfirmDialog(null, "Priority sets to default value. Would you like to proceed?", "Set Priority Validation", JOptionPane.YES_NO_OPTION);
						
					}
					
					if(ans == JOptionPane.YES_OPTION) {//If user click Yes, move the patient into the tail of the queue and store new trannsaction
						
						transactions.add(new Transaction(descriptNurseTxt.getText(), LocalDateTime.now(), patientId));
						queue.prioritize(patientId, priority);
						JOptionPane.showMessageDialog(null, "Saved successfully!", "Set priority", JOptionPane.INFORMATION_MESSAGE);
						clearTextFields(nursePanel);
					}
					
				}
				Patient nextPatient = patients.getElementById(queue.peek().getData());
				
				if(queue.size() > 0 && queue.peek().getPriority() == 0) {// Display next patient if any in waiting list 
					displayPatient(nextPatient, ihiNurseTxt, fnameNurseTxt, lnameNurseTxt, addressNurseTxt, cityNurseTxt, nationNurseTxt, dobNurseTxt, null);
					
				}
				
				rdbtn1.setSelected(true);
			}
		});
		
		terminateBtn.addActionListener(new ActionListener() {
			// Doctor's visit.
			public void actionPerformed(ActionEvent e) {
				
				if(ihiDocTxt.getText().isEmpty()) {//Just check if there is data showed(If not it might be no patients in queue) otherwise cannot save any data.
					JOptionPane.showMessageDialog(null, "Patient data missing. Cannot save!", "Validation error", JOptionPane.ERROR_MESSAGE);
					
				}else{
					//When visit terminates, remove the patient from the waiting list
					transactions.add(new Transaction(treatmentTxt.getText(), LocalDateTime.now(), Integer.parseInt(ihiDocTxt.getText())));
					
					if(queue.dequeue()) {// If the patient has correctly been removed from the queue
						
						JOptionPane.showMessageDialog(null, "Visit terminates!", "Doctor's treatment", JOptionPane.INFORMATION_MESSAGE);
						clearTextFields(doctorPane);
						
					}else {
						JOptionPane.showMessageDialog(null, "Patient cannot be removed from waiting list", "Doctor's treatment", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
				
				if(queue.size() > 0 && queue.priorityPeek().getPriority() > 0) {// Display next patient if any in queue and with priority already set
					
					displayPatient(patients.getElementById(queue.priorityPeek().getData()), ihiDocTxt, fNameDocTxt, lnameDocTxt, addrDocTxt, cityDocTxt, nationDocTxt, dobDocTxt, phoneDocTxt);
					priorityDocTxt.setText("" + queue.priorityPeek().getPriority());
				}
				
			}
		});
		
		btnShowRecords.addActionListener(new ActionListener() {
			// Show patient history
			public void actionPerformed(ActionEvent arg0) {
				
				String idStr = idHistTxt.getText().trim();
				try {
					// Retrieve a set of transaction based on patient id and then display data on the table.
					if(!idStr.isEmpty()) {
						
						int id = Integer.parseInt(idStr);
						
						if(patients.contains(id)) {
							
							// Get a set of transactions based on the patientID value
							Set<Transaction> set = transactions.getElements(x -> x.getPatientID() == id);
							//Display data onto the table(Screen)
							getDataForPatientHistory(modelHist, set);
							
						}else {
							JOptionPane.showMessageDialog(null, "Patient not found", "Patient History", JOptionPane.ERROR_MESSAGE);
						}
						
						
					}
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID is not correctly typed!", "Patient History", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			//Search for patient
			// Display patient data by patient Id searching
			public void actionPerformed(ActionEvent e) {
				
				String idString = searchTxt.getText().trim();
				
				
				try {
					
					if(!idString.isEmpty()) {
						
						int idForUpdate = Integer.parseInt(idString);						
						
						Patient pat = patients.getElementById(idForUpdate);

						if(pat != null) {
							displayPatient(pat, null, fnameEditTxt, lnameEditTxt, addressEditTxt, cityEdiTxt, nationEditTxt, dobEditTxt, phoneEditTxt);
							
						}else {
							JOptionPane.showMessageDialog(null, "Patient not found!", "Search Patient", JOptionPane.ERROR_MESSAGE);
							clearTextFields(searchPanel);
						}
						
					}
					
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID is not correctly typed!", "Search Patient", JOptionPane.ERROR_MESSAGE);
					clearTextFields(searchPanel);
					searchTxt.requestFocus();
				}
			}
			
		});
		
		btnUpdatePatientData.addActionListener(new ActionListener() {
			//Update data of the searched patient
			public void actionPerformed(ActionEvent e) {
				
				int id = Integer.parseInt(searchTxt.getText().trim());
				
				if(patients.contains(id)) {// Checks if patient already registered
					int ans = JOptionPane.showConfirmDialog(null, "Would you like to update data patient?", "Search Patient", JOptionPane.YES_NO_OPTION);
					
					if(ans == JOptionPane.YES_OPTION) {
						String fname = fnameEditTxt.getText();
						String lname = lnameEditTxt.getText();
						String city = cityEdiTxt.getText();
						String address = addressEditTxt.getText();
						String nation = nationEditTxt.getText();
						LocalDate date = LocalDate.parse(dobEditTxt.getText());
						String phone = phoneEditTxt.getText();
						Patient p = new Patient(id, fname, lname, address, city, nation, date, phone);
						
						if(patients.edit(p)) {//Just checks if the data patient have been updated into database
							JOptionPane.showMessageDialog(null, "Patient data updated successfully");
							transactions.add(new Transaction("Update patient data", LocalDateTime.now(), id));							
							
							
						}else {
							JOptionPane.showMessageDialog(null, "Patient data cannot be updated");
							
						}
						
					}
				}else {
					JOptionPane.showMessageDialog(null, "Patient not registered");
				}
				
			}
		});
		
		btnAddToWaiting.addActionListener(new ActionListener() {
			// Add to the waiting list the searched patient
			
			public void actionPerformed(ActionEvent arg0) {				
				// Checks first if the patient exists into database and if it's not already into the queue
				
				int id = Integer.parseInt(searchTxt.getText().trim());
				
				if(patients.contains(id) && !queue.contains(id)) {
					
					queue.enqueue(id);
					JOptionPane.showMessageDialog(null, "Patient added to waiting list");					
					
					
				}else {
					JOptionPane.showMessageDialog(null, "Patient not registered or already into waiting list");
				}
				
			}
		});
		
//####################################################################################################
//							TOOLBAR BUTTONS
//####################################################################################################		
		
		btnNurses.addActionListener(new ActionListener() {
			
			// Display Nurse window for the next patient in waiting list
			public void actionPerformed(ActionEvent e) {
				
				sendPanelInForeground("nurse");
				showToolbarButtonClicked(btnNurses);
				rdbtn1.setSelected(true);
				
				//Retrieve the next patient from the waiting list
				if(queue.size() > 0 && queue.peek().getPriority() == 0) {
					
					displayPatient(patients.getElementById(queue.peek().getData()), 
							ihiNurseTxt, fnameNurseTxt, lnameNurseTxt, addressNurseTxt, cityNurseTxt, nationNurseTxt, dobNurseTxt, null);
				}
				
				
			}
		});
		
		btnPatientHistory.addActionListener(new ActionListener() {
			
			//Display the Patient History window where patient transactions are visible
			public void actionPerformed(ActionEvent e) {
				
				sendPanelInForeground("patientHistory");
				showToolbarButtonClicked(btnPatientHistory);
				
			}
		});
		
		btnWaitingList.addActionListener(new ActionListener() {
			
			//Display Waiting list window. Waiting list of patient that need to be visited by Nurses
			public void actionPerformed(ActionEvent e) {
				
				sendPanelInForeground("waitingList");
				getDataForWaitingList(model);	
				showToolbarButtonClicked(btnWaitingList);
				
			}
		});
		
		btnReceptionist.addActionListener(new ActionListener() {
			
			// Receptionist Window
			public void actionPerformed(ActionEvent e) {
				
				sendPanelInForeground("receptionist");				
				showToolbarButtonClicked(btnReceptionist);
				
			}
		});
		
		btnSearchPatient.addActionListener(new ActionListener() {
			
			//Display Search for patient window			
			public void actionPerformed(ActionEvent arg0) {
				
				sendPanelInForeground("search");
				showToolbarButtonClicked(btnSearchPatient);
			}
		});
		
		
		btnDoctors.addActionListener(new ActionListener() {
			//Display doctor window
			// If any patient in the waiting list then it shows the next patient with higher priority who must be visited
			
			public void actionPerformed(ActionEvent arg0) {
				
				sendPanelInForeground("doctor");
				showToolbarButtonClicked(btnDoctors);
				
				//Get the next patient from a priority list
				if(queue.size() > 0 && queue.priorityPeek().getData() > 0) {
					
					displayPatient(patients.getElementById(queue.priorityPeek().getData()), 
							ihiDocTxt, fNameDocTxt, lnameDocTxt, addrDocTxt, cityDocTxt, nationDocTxt, dobDocTxt, phoneDocTxt);
					priorityDocTxt.setText("" + queue.priorityPeek().getPriority());
				}
			}
		});
		
		
	}
	
	
//####################################################################################################
//										UTILITY METHODS
//####################################################################################################	
	
	private void displayPatient(Patient nextPatient, JTextField... params) {
		// Display patient data to the text fields available onto panel. Needs params because the number of text fields vary between panels.
		for(int i = 0; i < params.length; i++) {
			
			if(params[i] != null) {
				
				switch(i) {
					case 0:
						params[i].setText("" + nextPatient.getId());
						break;
					case 1:
						params[i].setText(nextPatient.getFirstName());
						break;
					case 2:
						params[i].setText(nextPatient.getLastName());
						break;
					case 3:
						params[i].setText(nextPatient.getAddress());
						break;
					case 4:
						params[i].setText(nextPatient.getCity());
						break;
					case 5:
						params[i].setText(nextPatient.getNationality());
						break;
					case 6:
						params[i].setText(nextPatient.getDob().toString());
						break;
					case 7:
						params[i].setText(nextPatient.getPhoneNumber()); 
						break;
					default:
						break;
				}
				
			}
		}
	
				
	}
	
	private void showToolbarButtonClicked(JButton btn) {
		// Change borders to the clicked button on the toolbar
		btnReceptionist.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDoctors.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnSearchPatient.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnWaitingList.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnPatientHistory.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnNurses.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btn.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
	}
	
	private void sendPanelInForeground(String panelName) {
		// Set the visibility to the selected panel and set the others no visible.
		recepPanel.setVisible(false);
		nursePanel.setVisible(false);
		doctorPane.setVisible(false);
		waitingList.setVisible(false);
		patientHistPanel.setVisible(false);
		searchPanel.setVisible(false);
		
		switch(panelName) {
			case "receptionist":
				recepPanel.setVisible(true);
				break;
			case "nurse":
				nursePanel.setVisible(true);
				break;
			case "doctor":
				doctorPane.setVisible(true);
				break;
			case "waitingList":
				waitingList.setVisible(true);
				break;
			case "patientHistory":
				patientHistPanel.setVisible(true);
				break;
			case "search":
				searchPanel.setVisible(true);
				break;
			default:
				break;
		}
		
	}
	
	private String getPriorityFromRadioButton(ButtonGroup group) {
		// Method to retrieve the value from the selected radio button. Retrieved from stackoverflow.
		for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
		return null;
	}
	
	private void clearTextFields(JPanel panel) {
		// Clear all text field in a panel
		for(Component comp : panel.getComponents()) {
			if(comp instanceof JTextComponent ) {
				((JTextComponent)comp).setText("");
			}
		}
	}
	
	private void getDataForWaitingList(DefaultTableModel model) {		
		
		// Gets data from the waiting list. So It will get a queue in order of registration, no prirority involved.
		List<Node> list = queue.getQueue();
		clearRowsInTable(model);
		
		for(int i = 0; i < list.size(); i++) {
			Patient p = patients.getElementById(list.get(i).getData());
			model.addRow(new Object[] {p.getId(), p.getFirstName(), p.getLastName(), p.getDob().toString(), list.get(i).getPriority()});
			
		}
	}
	
	private void clearRowsInTable(DefaultTableModel model) {
		int rows = model.getRowCount();
		for(int j = rows - 1; j >= 0; j--) {
			model.removeRow(j);
		}
	}
	
	private void getDataForPatientHistory(DefaultTableModel model, Set<Transaction> set) {
		//Get all transaction per patient
		clearRowsInTable(model);
		for(Transaction t : set) {
			model.addRow(new Object[] {t.getDescription(), t.getDate().toString()});
		}
	}
}
