package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Controller.Base;
import Controller.BaseMethods;
import Controller.Excel.LoadZaFile;
import Controller.Services.CRUD;
import Controller.Services.TimeMeasurementHeaderServices;
import Model.TimeMeasurementDetail;
import Model.TimeMeasurementHeader;
import Model.ZA;
import TableParameters.DetailTableItemModel;
import TableParameters.OddRowColorRenderer;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultRowSorter;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

public class AddMeasurements extends JFrame {

	private JPanel contentPane;
	private JTextField txtFz;
	private JTextField txtZaCode;
	private JTextField txtZaType;
	private JTextField txtMeasurementName;

	private static DefaultTableModel defaultTableModel;
	private TableRowSorter<DefaultTableModel> sorter;
	private DetailTableItemModel tiModel;
	private static JLabel lblBackground;

	private static int selectedRow = -1;

	private static JTable tblMain;

	private JTextField txtLg;
	private JTextField txtStartTime;
	private JTextField txtEndTime;

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public AddMeasurements(List<TimeMeasurementDetail> detailList) throws Exception {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Image frameIcon = Toolkit.getDefaultToolkit().getImage(Base.icon);
		setIconImage(frameIcon);
		setTitle(Base.FRAME_CAPTION);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setPreferredSize(new Dimension(Base.WIDTH, Base.HEIGHT));
		getContentPane().add(contentPane);
		contentPane.setLayout(null);

		pack();
		setLocationRelativeTo(null);
		contentPane.setLayout(null);

		List<ZA> zaList = LoadZaFile.LoadZA();

		JPanel pnlMeasurementData = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		pnlMeasurementData.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(100, 149, 237), new Color(160, 160, 160)),
				"Данни от измерване", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlMeasurementData.setBounds(25, 73, 1317, 60);
		pnlMeasurementData.setBackground(new Color(255, 255, 255, 200));
		contentPane.add(pnlMeasurementData);
		GridBagLayout gbl_pnlMeasurementData = new GridBagLayout();
		gbl_pnlMeasurementData.columnWidths = new int[] { 50, 100, 50, 100, 110, 400, 50, 100, 50, 50, 100, 0 };
		gbl_pnlMeasurementData.rowHeights = new int[] { 35, 0 };
		gbl_pnlMeasurementData.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pnlMeasurementData.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlMeasurementData.setLayout(gbl_pnlMeasurementData);

		JLabel lblFz = new JLabel("FZ");
		GridBagConstraints gbc_lblFz = new GridBagConstraints();
		gbc_lblFz.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFz.insets = new Insets(0, 0, 0, 5);
		gbc_lblFz.gridx = 0;
		gbc_lblFz.gridy = 0;
		pnlMeasurementData.add(lblFz, gbc_lblFz);
		lblFz.setFont(Base.DEFAULT_FONT);

		txtFz = new JTextField();
		GridBagConstraints gbc_txtFz = new GridBagConstraints();
		gbc_txtFz.fill = GridBagConstraints.BOTH;
		gbc_txtFz.insets = new Insets(0, 0, 0, 5);
		gbc_txtFz.gridx = 1;
		gbc_txtFz.gridy = 0;
		pnlMeasurementData.add(txtFz, gbc_txtFz);
		txtFz.setFont(Base.DEFAULT_FONT);

		JLabel lblCode = new JLabel("Код");
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCode.insets = new Insets(0, 0, 0, 5);
		gbc_lblCode.gridx = 2;
		gbc_lblCode.gridy = 0;
		pnlMeasurementData.add(lblCode, gbc_lblCode);
		lblCode.setFont(Base.DEFAULT_FONT);

		txtZaCode = new JTextField();
		GridBagConstraints gbc_txtZaCode = new GridBagConstraints();
		gbc_txtZaCode.fill = GridBagConstraints.BOTH;
		gbc_txtZaCode.insets = new Insets(0, 0, 0, 5);
		gbc_txtZaCode.gridx = 3;
		gbc_txtZaCode.gridy = 0;
		pnlMeasurementData.add(txtZaCode, gbc_txtZaCode);
		txtZaCode.setFont(Base.DEFAULT_FONT);

		JLabel lblDesc = new JLabel("Описание");
		GridBagConstraints gbc_lblDesc = new GridBagConstraints();
		gbc_lblDesc.anchor = GridBagConstraints.WEST;
		gbc_lblDesc.insets = new Insets(0, 0, 0, 5);
		gbc_lblDesc.gridx = 4;
		gbc_lblDesc.gridy = 0;
		pnlMeasurementData.add(lblDesc, gbc_lblDesc);
		lblDesc.setFont(Base.DEFAULT_FONT);

		JComboBox<ZA> cboZA = new JComboBox<ZA>();
		GridBagConstraints gbc_cboZA = new GridBagConstraints();
		gbc_cboZA.fill = GridBagConstraints.BOTH;
		gbc_cboZA.insets = new Insets(0, 0, 0, 5);
		gbc_cboZA.gridx = 5;
		gbc_cboZA.gridy = 0;
		pnlMeasurementData.add(cboZA, gbc_cboZA);
		cboZA.setModel(new DefaultComboBoxModel<ZA>(zaList.toArray(new ZA[0])));
		cboZA.setRenderer(new ZaRenderer());
		cboZA.setFont(Base.DEFAULT_FONT);
		cboZA.addItemListener(e -> {
			ZA za = (ZA) e.getItem();
			txtZaCode.setText(String.valueOf(za.getCode()));
			txtZaType.setText(za.getType());
		});

		JLabel lblZa = new JLabel("ZA");
		GridBagConstraints gbc_lblZa = new GridBagConstraints();
		gbc_lblZa.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblZa.insets = new Insets(0, 0, 0, 5);
		gbc_lblZa.gridx = 6;
		gbc_lblZa.gridy = 0;
		pnlMeasurementData.add(lblZa, gbc_lblZa);
		lblZa.setFont(Base.DEFAULT_FONT);

		txtZaType = new JTextField();
		GridBagConstraints gbc_txtZaType = new GridBagConstraints();
		gbc_txtZaType.fill = GridBagConstraints.BOTH;
		gbc_txtZaType.insets = new Insets(0, 0, 0, 5);
		gbc_txtZaType.gridx = 7;
		gbc_txtZaType.gridy = 0;
		pnlMeasurementData.add(txtZaType, gbc_txtZaType);
		txtZaType.setFont(Base.DEFAULT_FONT);

		JCheckBox chckbxTg = new JCheckBox("TG");
		GridBagConstraints gbc_chckbxTg = new GridBagConstraints();
		gbc_chckbxTg.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxTg.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxTg.gridx = 8;
		gbc_chckbxTg.gridy = 0;
		pnlMeasurementData.add(chckbxTg, gbc_chckbxTg);
		chckbxTg.setFont(Base.DEFAULT_FONT);
		chckbxTg.setSelected(true);
		chckbxTg.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxTg.setBackground(new Color(255, 255, 255, 200));

		JLabel lblLg = new JLabel("LG");
		GridBagConstraints gbc_lblLg = new GridBagConstraints();
		gbc_lblLg.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLg.insets = new Insets(0, 0, 0, 5);
		gbc_lblLg.gridx = 9;
		gbc_lblLg.gridy = 0;
		pnlMeasurementData.add(lblLg, gbc_lblLg);
		lblLg.setFont(Base.DEFAULT_FONT);

		txtLg = new JTextField();
		GridBagConstraints gbc_txtLg = new GridBagConstraints();
		gbc_txtLg.fill = GridBagConstraints.BOTH;
		gbc_txtLg.gridx = 10;
		gbc_txtLg.gridy = 0;
		pnlMeasurementData.add(txtLg, gbc_txtLg);
		txtLg.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				selectedRow = tblMain.getSelectedRow();
				System.out.println(selectedRow);
				if (selectedRow == -1) {
					TimeMeasurementDetail tmDetail = new TimeMeasurementDetail(null, Integer.parseInt(txtFz.getText()),
							Integer.parseInt(txtLg.getText()), chckbxTg.isSelected(),
							Integer.parseInt(txtZaCode.getText()));
					detailList.add(tmDetail);

					tiModel.addRow(tmDetail);
					BaseMethods.ResizeColumnWidth(tblMain);
					// SortTable();
				} else {
					TimeMeasurementDetail tmDetail = tiModel.getDetailAt(tblMain.getSelectedRow());
					int getIndex = detailList.indexOf(tmDetail);

					tmDetail.setFz(Integer.parseInt(txtFz.getText()));
					tmDetail.setZaCode(Integer.parseInt(txtZaCode.getText()));
					tmDetail.setTg(chckbxTg.isSelected());
					tmDetail.setLg(Integer.parseInt(txtLg.getText()));

					detailList.set(getIndex, tmDetail);

					tiModel.fireTableRowsUpdated(selectedRow, selectedRow);
				}
				txtFz.setText("");
				txtFz.requestFocus();
				txtLg.setText("");
				txtZaCode.setText("");
				cboZA.setSelectedIndex(0);
				chckbxTg.setSelected(true);
				selectedRow = -1;
			}
		});
		txtLg.setFont(Base.DEFAULT_FONT);

		JPanel pnlMeasurement = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		pnlMeasurement.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(100, 149, 237), new Color(160, 160, 160)), "Измерване",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlMeasurement.setBounds(25, 7, 955, 60);
		pnlMeasurement.setBackground(new Color(255, 255, 255, 200));
		contentPane.add(pnlMeasurement);
		GridBagLayout gbl_pnlMeasurement = new GridBagLayout();
		gbl_pnlMeasurement.columnWidths = new int[] { 110, 234, 125, 100, 110, 100, 0 };
		gbl_pnlMeasurement.rowHeights = new int[] { 30, 0 };
		gbl_pnlMeasurement.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlMeasurement.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlMeasurement.setLayout(gbl_pnlMeasurement);

		JLabel lblMeasurementName = new JLabel("Описание");
		GridBagConstraints gbc_lblMeasurementName = new GridBagConstraints();
		gbc_lblMeasurementName.anchor = GridBagConstraints.WEST;
		gbc_lblMeasurementName.insets = new Insets(0, 0, 0, 5);
		gbc_lblMeasurementName.gridx = 0;
		gbc_lblMeasurementName.gridy = 0;
		pnlMeasurement.add(lblMeasurementName, gbc_lblMeasurementName);
		lblMeasurementName.setFont(Base.DEFAULT_FONT);

		txtMeasurementName = new JTextField();
		GridBagConstraints gbc_txtMeasurementName = new GridBagConstraints();
		gbc_txtMeasurementName.insets = new Insets(0, 0, 0, 5);
		gbc_txtMeasurementName.fill = GridBagConstraints.BOTH;
		gbc_txtMeasurementName.gridx = 1;
		gbc_txtMeasurementName.gridy = 0;
		pnlMeasurement.add(txtMeasurementName, gbc_txtMeasurementName);
		txtMeasurementName.setFont(Base.DEFAULT_FONT);

		JLabel lblStartTime = new JLabel("Начало, час");
		GridBagConstraints gbc_lblStartTime = new GridBagConstraints();
		gbc_lblStartTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStartTime.insets = new Insets(0, 0, 0, 5);
		gbc_lblStartTime.gridx = 2;
		gbc_lblStartTime.gridy = 0;
		pnlMeasurement.add(lblStartTime, gbc_lblStartTime);
		lblStartTime.setFont(Base.DEFAULT_FONT);

		txtStartTime = new JTextField();
		GridBagConstraints gbc_txtStartTime = new GridBagConstraints();
		gbc_txtStartTime.insets = new Insets(0, 0, 0, 5);
		gbc_txtStartTime.fill = GridBagConstraints.BOTH;
		gbc_txtStartTime.gridx = 3;
		gbc_txtStartTime.gridy = 0;
		pnlMeasurement.add(txtStartTime, gbc_txtStartTime);
		txtStartTime.setFont(Base.DEFAULT_FONT);

		JLabel lblEndTime = new JLabel("Край, час");
		GridBagConstraints gbc_lblEndTime = new GridBagConstraints();
		gbc_lblEndTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEndTime.insets = new Insets(0, 0, 0, 5);
		gbc_lblEndTime.gridx = 4;
		gbc_lblEndTime.gridy = 0;
		pnlMeasurement.add(lblEndTime, gbc_lblEndTime);
		lblEndTime.setFont(Base.DEFAULT_FONT);

		txtEndTime = new JTextField();
		GridBagConstraints gbc_txtEndTime = new GridBagConstraints();
		gbc_txtEndTime.fill = GridBagConstraints.BOTH;
		gbc_txtEndTime.gridx = 5;
		gbc_txtEndTime.gridy = 0;
		pnlMeasurement.add(txtEndTime, gbc_txtEndTime);
		txtEndTime.setFont(Base.DEFAULT_FONT);

		JButton btnSave = new JButton("Запази");
		btnSave.setForeground(Color.WHITE);
		btnSave.setBackground(Base.BUTTON_COLOR);
		btnSave.setFont(Base.DEFAULT_FONT);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					CRUD.SaveAll(new TimeMeasurementHeader(txtMeasurementName.getText(), txtStartTime.getText(), txtEndTime.getText()), detailList);
					dispose();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSave.setBounds(1116, 706, Base.BUTTON_WIDTH, Base.BUTTON_HEIGHT);
		contentPane.add(btnSave);

		// create table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 150, Base.WIDTH - 50, 500);
		scrollPane.getViewport().setBackground(Color.white);
		contentPane.add(scrollPane);

		sorter = new TableRowSorter<DefaultTableModel>(defaultTableModel);

		tblMain = new JTable() {
//			public boolean isCellEditable(int row, int column) {
//				return false;
//			};
		};
		tblMain.setBounds(0, 0, 0, 0);
		tblMain.setFont(Base.DEFAULT_FONT);
		tblMain.setRowHeight(26);
		tblMain.getTableHeader().setFont(Base.DEFAULT_FONT);
		tblMain.getTableHeader().setResizingAllowed(true);
		scrollPane.setViewportView(tblMain);
		tblMain.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblMain.getTableHeader().setOpaque(false);
		tblMain.setCellSelectionEnabled(false);
		tblMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblMain.setRowSelectionAllowed(true);
		tblMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TimeMeasurementDetail tmDetail = tiModel.getDetailAt(tblMain.getSelectedRow());
				txtFz.setText(String.valueOf(tmDetail.getFz()));
				txtFz.requestFocus();
				txtLg.setText(String.valueOf(tmDetail.getLg()));
				chckbxTg.setSelected(tmDetail.getTg());
				cboZA.setSelectedIndex(tmDetail.getZaCode() - 1);
			}
		});

		OddRowColorRenderer orcr = new OddRowColorRenderer();
		tblMain.setDefaultRenderer(Object.class, orcr);

		tiModel = new DetailTableItemModel(detailList, zaList);
		tblMain.setModel(tiModel);
		BaseMethods.ResizeColumnWidth(tblMain);

		SetBackgroundPicture();
		setVisible(true);
	}

	private void SortTable() {
		DefaultRowSorter sorter = ((DefaultRowSorter) tblMain.getRowSorter());
		ArrayList list = new ArrayList();
		list.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
		sorter.setSortKeys(list);
		sorter.sort();
	}

	private class ZaRenderer extends DefaultListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if (value instanceof ZA) {
				ZA za = (ZA) value;
				value = za.getDesc_bg();
			}
			return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
	}

	private void SetBackgroundPicture() {
		ImageIcon imageIcon = new ImageIcon(Base.backgroundPic);
		lblBackground = new JLabel(imageIcon);
		lblBackground.setBounds(0, 0, Base.WIDTH, Base.HEIGHT);
		contentPane.add(lblBackground);
	}
}
