package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Controller.Base;
import Controller.BaseMethods;
import Model.TimeMeasurementHeader;
import Service.TimeMeasurementHeaderServices;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Header extends JFrame {

	private JPanel contentPane;

	private static DefaultTableModel defaultTableModel;
	private TableRowSorter<DefaultTableModel> sorter;
	
	private static JLabel lblBackground;

	private static String header[] = { "#", "Описание", "Дата на създаване" };

	private static JTable tblMain;

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public Header() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		// create table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 25, Base.WIDTH - 50, Base.HEIGHT - 100);
		scrollPane.getViewport().setBackground(Color.white);
		contentPane.add(scrollPane);

		defaultTableModel = new DefaultTableModel(0, 0);

		defaultTableModel.setColumnIdentifiers(header);

		sorter = new TableRowSorter<DefaultTableModel>(defaultTableModel);

		tblMain = new JTable(defaultTableModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		tblMain.setBounds(0, 0, 0, 0);
		tblMain.setFont(Base.DEFAULT_FONT);
		tblMain.setRowHeight(26);
		tblMain.getTableHeader().setFont(Base.DEFAULT_FONT);
		tblMain.getTableHeader().setResizingAllowed(true);
		scrollPane.setViewportView(tblMain);
		// tblMain.setModel(defaultTableModel);
		tblMain.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblMain.setRowSorter(sorter);
		tblMain.getTableHeader().setOpaque(false);
		//tblMain.getTableHeader().setBackground(Color.blue);
		

		JButton btnAddNew = new JButton("Ново измерване");
		btnAddNew.setForeground(Color.WHITE);
		btnAddNew.setBackground(Base.BUTTON_COLOR);
		btnAddNew.setFont(Base.DEFAULT_FONT);
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new AddMeasurements();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAddNew.setBounds(1115, 705, Base.BUTTON_WIDTH, Base.BUTTON_HEIGHT);
		contentPane.add(btnAddNew);

		SetBackgroundPicture();
		setVisible(true);
		FillTable();
		BaseMethods.ResizeColumnWidth(tblMain);
	}

	private static void FillTable() throws Exception {
		List<TimeMeasurementHeader> data = TimeMeasurementHeaderServices.GetRecords();

		defaultTableModel.setRowCount(0);

		for (TimeMeasurementHeader entry : data) {

			defaultTableModel.addRow(new Object[] { entry.getId(), entry.getName(), BaseMethods.ExtractDate(entry.getCreateDate()) });
		}
	}
	
	private void SetBackgroundPicture() {
		ImageIcon imageIcon = new ImageIcon(Base.backgroundPic);
		lblBackground = new JLabel(imageIcon);
		lblBackground.setBounds(0, 0, Base.WIDTH, Base.HEIGHT);
		contentPane.add(lblBackground);
	}
}
