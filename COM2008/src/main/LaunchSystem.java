package main;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import GUI.Frame;

public class LaunchSystem {
	public static void main(String[] args) {
		// try to use alternative look and feel for swing
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, do nothing;
		}
		// launch starting point for the system
		new Frame("COM 2008/3008 University Management System");
	}

}
