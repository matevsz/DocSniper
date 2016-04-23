package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CMainFrame extends JFrame {

	/**
	 * serialized version UID
	 */
	private static final long serialVersionUID = 1L;
	private static final String CHECK_BOX_NAME_1 = "ITE explore";
	private static final String CHECK_BOX_NAME_2 = "Science Direct";
	private static final String CHECK_BOX_NAME_3 = "Sigous";
	private static final String CHECK_BOX_NAME_4 = "Springer";
	
	private JPanel m_oContentPane = null;
	static private CMainFrame m_oMainFrame = null;
	private JTextField m_oFileSearchTextField = null;
	private JTextField m_oKeywordSearchTestField = null;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CLogger.createLogFile();
					m_oMainFrame = new CMainFrame();
					CLogger.log(CLogger.INFO, "Main window is created");
					m_oMainFrame.setVisible(true);
					CLogger.log(CLogger.INFO, "Main window is visable");
				} catch (Exception e) {
					CLogger.log(CLogger.INFO, "Can not create main window " + e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CMainFrame() {
		setMinimumSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		m_oContentPane = new JPanel();
		m_oContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(m_oContentPane);
		
		m_oFileSearchTextField = new JTextField();
		m_oFileSearchTextField.setColumns(10);
		
		final JButton oSearchNewDocButton = new JButton("Search new documents");
		oSearchNewDocButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent a_oAction) 
			{
				CLogger.log(CLogger.INFO, oSearchNewDocButton.getText() + " - Pressed; With \"" + m_oFileSearchTextField.getText() + "\" value" );
			}
		});
		
		JScrollPane oScrollPane = new JScrollPane();
		
		m_oKeywordSearchTestField = new JTextField();
		m_oKeywordSearchTestField.setColumns(10);
		
		final JButton oSearchKeyword = new JButton("SearchKeywords");
		oSearchKeyword.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent a_oAction)
			{
				CLogger.log(CLogger.INFO, oSearchKeyword.getText() + " - Pressed; With \"" + m_oKeywordSearchTestField.getText() + "\" value" );
			}
		});
		
		JPanel oPanel = new JPanel();
		GroupLayout oGroupLayout = new GroupLayout(m_oContentPane);
		oGroupLayout.setHorizontalGroup(
			oGroupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(oGroupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(oGroupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(oScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
						.addComponent(m_oKeywordSearchTestField, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
						.addComponent(m_oFileSearchTextField, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(oGroupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(oPanel, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
						.addComponent(oSearchNewDocButton, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
						.addComponent(oSearchKeyword, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
					.addContainerGap())
		);
		oGroupLayout.setVerticalGroup(
			oGroupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(oGroupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(oGroupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_oFileSearchTextField, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(oSearchNewDocButton))
					.addGap(18)
					.addGroup(oGroupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(oGroupLayout.createSequentialGroup()
							.addComponent(oScrollPane, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
							.addGap(18))
						.addGroup(oGroupLayout.createSequentialGroup()
							.addComponent(oPanel, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(oGroupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_oKeywordSearchTestField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(oSearchKeyword))
					.addContainerGap())
		);
		
		JTextPane oCheckBoxTitle = new JTextPane();
		oCheckBoxTitle.setText("Choosen sites");
		oCheckBoxTitle.setBackground(oPanel.getBackground());
		
		JCheckBox oCheckBox_1 = new JCheckBox(CHECK_BOX_NAME_1);
		
		JCheckBox oCheckBox_2 = new JCheckBox(CHECK_BOX_NAME_2);
		
		JCheckBox oCheckBox_3 = new JCheckBox(CHECK_BOX_NAME_3);
		
		JCheckBox oCheckBox_4 = new JCheckBox(CHECK_BOX_NAME_4);
		GroupLayout oCheckBoxGroupLayout = new GroupLayout(oPanel);
		oCheckBoxGroupLayout.setHorizontalGroup(
			oCheckBoxGroupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(oCheckBoxGroupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(oCheckBoxGroupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(oCheckBox_4)
						.addComponent(oCheckBox_3)
						.addComponent(oCheckBox_2)
						.addComponent(oCheckBox_1)
						.addComponent(oCheckBoxTitle, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
					.addContainerGap())
		);
		oCheckBoxGroupLayout.setVerticalGroup(
			oCheckBoxGroupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(oCheckBoxGroupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(oCheckBoxTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(oCheckBox_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(oCheckBox_2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(oCheckBox_3)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(oCheckBox_4)
					.addContainerGap(177, Short.MAX_VALUE))
		);
		oPanel.setLayout(oCheckBoxGroupLayout);
		
		table = new JTable();
		oScrollPane.setViewportView(table);
		m_oContentPane.setLayout(oGroupLayout);
		
	
	}
}
