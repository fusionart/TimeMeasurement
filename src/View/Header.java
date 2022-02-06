package View;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Controller.Base;
import Controller.BaseMethods;
import Model.TimeMeasurementHeader;
import Service.TimeMeasurementHeaderServices;
import TableParameters.DetailTableItemModel;
import TableParameters.HeaderTableItemModel;
import TableParameters.OddRowColorRenderer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Header extends JFrame {

	private JPanel contentPane;

	private static HeaderTableItemModel tiModel;
	
	private static JLabel lblBackground;

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

		tblMain = new JTable() {
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
		tblMain.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblMain.getTableHeader().setOpaque(false);
		
		OddRowColorRenderer orcr = new OddRowColorRenderer();
		tblMain.setDefaultRenderer(Object.class, orcr);		
		
		JButton btnAddNew = new JButton("Ново измерване");
		btnAddNew.setForeground(Color.WHITE);
		btnAddNew.setBackground(Base.BUTTON_COLOR);
		btnAddNew.setFont(Base.DEFAULT_FONT);
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AddMeasurements addMeasurement = new AddMeasurements();
					addMeasurement.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							super.windowClosed(e);
							FillTable();
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAddNew.setBounds(1115, 705, Base.BUTTON_WIDTH, Base.BUTTON_HEIGHT);
		contentPane.add(btnAddNew);

		FillTable();
		SetBackgroundPicture();
		setVisible(true);
	}

	private static void FillTable() {
		List<TimeMeasurementHeader> data = null;
		try {
			data = TimeMeasurementHeaderServices.GetRecords();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (data.size() != 0) {
			tiModel = new HeaderTableItemModel(data);
			tblMain.setModel(tiModel);
			BaseMethods.ResizeColumnWidth(tblMain);
			
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

	        tblMain.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		}
	}
	
	private void SetBackgroundPicture() {
		ImageIcon imageIcon = new ImageIcon(Base.backgroundPic);
		lblBackground = new JLabel(imageIcon);
		lblBackground.setBounds(0, 0, Base.WIDTH, Base.HEIGHT);
		contentPane.add(lblBackground);
	}
}
