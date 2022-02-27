package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;

import Controller.Base;
import Controller.BaseConstants;
import Controller.BaseMethods;
import Controller.Services.CRUD;
import Model.TimeMeasurementDetail;
import Model.TimeMeasurementHeader;
import TableParameters.HeaderTableItemModel;
import TableParameters.OddRowColorRenderer;

public class Header extends JFrame {

	private JPanel contentPane;

	private final int POPUP_MENU_WIDTH = 150;
	private final int POPUP_MENU_ITEM_HEIGHT = 30;

	private static HeaderTableItemModel tiModel;

	private static JLabel lblBackground;

	private static JTable tblMain;

	private static List<TimeMeasurementDetail> detailList = new ArrayList<>();

	private static TimeMeasurementHeader tmHeaderSelected;

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public Header() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image frameIcon = Toolkit.getDefaultToolkit().getImage(Base.icon);
		setIconImage(frameIcon);
		setTitle(BaseConstants.FRAME_CAPTION);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setPreferredSize(new Dimension(BaseConstants.WIDTH, BaseConstants.HEIGHT));
		getContentPane().add(contentPane);
		contentPane.setLayout(null);

		pack();
		setLocationRelativeTo(null);
		contentPane.setLayout(null);

		// create table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 25, BaseConstants.WIDTH - 50, BaseConstants.HEIGHT - 100);
		scrollPane.getViewport().setBackground(Color.white);
		contentPane.add(scrollPane);

		tblMain = new JTable() {
//			public boolean isCellEditable(int row, int column) {
//				return false;
//			};
		};
		tblMain.setBounds(0, 0, 0, 0);
		tblMain.setFont(BaseConstants.DEFAULT_FONT);
		tblMain.setRowHeight(26);
		tblMain.getTableHeader().setFont(BaseConstants.DEFAULT_FONT);
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
				tmHeaderSelected = tiModel.getHeaderAt(tblMain.getSelectedRow());
			}
		});

		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						int rowAtPoint = tblMain
								.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), tblMain));
						if (rowAtPoint > -1) {
							tblMain.setRowSelectionInterval(rowAtPoint, rowAtPoint);
							tmHeaderSelected = tiModel.getHeaderAt(tblMain.getSelectedRow());
						}
					}
				});
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}
		});
		JMenuItem copyItem = new JMenuItem("Копиране");
		copyItem.setFont(BaseConstants.DEFAULT_FONT);
		copyItem.setPreferredSize(new Dimension(POPUP_MENU_WIDTH, POPUP_MENU_ITEM_HEIGHT));
		popupMenu.add(copyItem);
		copyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					detailList = CRUD.getAllDetails(tmHeaderSelected);
					AddMeasurements addMeasurement = new AddMeasurements(detailList);
					addMeasurement.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							super.windowClosed(e);
							tmHeaderSelected = null;
							FillTable();
						}
					});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JMenuItem deleteItem = new JMenuItem("Изтрий");
		deleteItem.setFont(BaseConstants.DEFAULT_FONT);
		deleteItem.setPreferredSize(new Dimension(POPUP_MENU_WIDTH, POPUP_MENU_ITEM_HEIGHT));
		popupMenu.add(deleteItem);
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] options = { "Да", "Не" };
				int response = JOptionPane.showOptionDialog(null, "Сигурни ли сте, че искате да изтриете записа?",
						"Внимание", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

				if (response == 0) {
					try {
						CRUD.delete(tmHeaderSelected);
						tiModel.deleteRow(tmHeaderSelected);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		tblMain.setComponentPopupMenu(popupMenu);

		OddRowColorRenderer orcr = new OddRowColorRenderer();
		tblMain.setDefaultRenderer(Object.class, orcr);

		JButton btnAddNew = new JButton("Ново измерване");
		btnAddNew.setForeground(Color.WHITE);
		btnAddNew.setBackground(BaseConstants.BUTTON_COLOR);
		btnAddNew.setFont(BaseConstants.DEFAULT_FONT);
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					detailList.clear();
					AddMeasurements addMeasurement = new AddMeasurements(detailList);
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
		btnAddNew.setBounds(1115, 705, BaseConstants.BUTTON_WIDTH, BaseConstants.BUTTON_HEIGHT);
		contentPane.add(btnAddNew);

		JButton btnCopy = new JButton("Копиране");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (tmHeaderSelected != null) {
						detailList = CRUD.getAllDetails(tmHeaderSelected);
						AddMeasurements addMeasurement = new AddMeasurements(detailList);
						addMeasurement.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								super.windowClosed(e);
								tmHeaderSelected = null;
								FillTable();
							}
						});
					} else {
						JOptionPane.showMessageDialog(null, "Моля, изберете запис.", BaseConstants.ERROR,
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCopy.setBounds(878, 705, BaseConstants.BUTTON_WIDTH, BaseConstants.BUTTON_HEIGHT);
		btnCopy.setForeground(Color.WHITE);
		btnCopy.setBackground(BaseConstants.BUTTON_COLOR);
		btnCopy.setFont(BaseConstants.DEFAULT_FONT);
		contentPane.add(btnCopy);

		FillTable();
		SetBackgroundPicture();
		setVisible(true);
	}

	private static void FillTable() {
		List<TimeMeasurementHeader> data = null;
		try {
			data = CRUD.getAllHeaders();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tiModel = new HeaderTableItemModel(data);
		tblMain.setModel(tiModel);
		BaseMethods.resizeColumnWidth(tblMain);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		tblMain.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	}

	private void SetBackgroundPicture() {
		ImageIcon imageIcon = new ImageIcon(Base.backgroundPic);
		lblBackground = new JLabel(imageIcon);
		lblBackground.setBounds(0, 0, BaseConstants.WIDTH, BaseConstants.HEIGHT);
		contentPane.add(lblBackground);
	}
}
