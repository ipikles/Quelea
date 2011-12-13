/*
 * This file is part of Quelea, free projection software for churches. Copyright
 * (C) 2011 Michael Berry
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.quelea.windows.main.ribbon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenu;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary;
import org.quelea.Application;
import org.quelea.ScheduleSaver;
import org.quelea.languages.LabelGrabber;
import org.quelea.print.Printer;
import org.quelea.utils.Utils;
import org.quelea.windows.main.MainPanel;
import org.quelea.windows.main.ribbon.secondPanels.ExitPanelDrawer;
import org.quelea.windows.main.ribbon.secondPanels.NewPanelDrawer;
import org.quelea.windows.main.ribbon.secondPanels.OpenPanelDrawer;
import org.quelea.windows.main.ribbon.secondPanels.OptionsPanelDrawer;
import org.quelea.windows.main.ribbon.secondPanels.PrintPanelDrawer;
import org.quelea.windows.main.ribbon.secondPanels.SaveAsPanelDrawer;
import org.quelea.windows.main.ribbon.secondPanels.SavePanelDrawer;
import org.quelea.windows.options.OptionsDialog;

/**
 * The menu on the ribbon component.
 *
 * @author Michael
 */
public class RibbonMenu extends RibbonApplicationMenu {

    private final OptionsDialog optionsDialog;

    /**
     * Create the ribbon menu.
     */
    public RibbonMenu() {
        optionsDialog = new OptionsDialog(Application.get().getMainWindow());

        RibbonApplicationMenuEntryPrimary newMenuEntry = new RibbonApplicationMenuEntryPrimary(
                RibbonUtils.getRibbonIcon("icons/filenew.png", 100, 100), LabelGrabber.INSTANCE.getLabel("new.schedule.button"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (confirmClear()) {
                    Application.get().getMainWindow().getMainPanel().getSchedulePanel().getScheduleList().clearSchedule();
                }
            }
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        newMenuEntry.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {

            @Override
            public void menuEntryActivated(JPanel pnl) {
                new NewPanelDrawer().draw(pnl);
            }
        });
        addMenuEntry(newMenuEntry);
        RibbonApplicationMenuEntryPrimary openMenuEntry = new RibbonApplicationMenuEntryPrimary(
                RibbonUtils.getRibbonIcon("icons/fileopen.png", 100, 100), LabelGrabber.INSTANCE.getLabel("open.schedule.button"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (confirmClear()) {
                    JFileChooser chooser = Utils.getScheduleFileChooser();
                    if (chooser.showOpenDialog(Application.get().getMainWindow()) == JFileChooser.APPROVE_OPTION) {
                        Application.get().openSchedule(chooser.getSelectedFile());
                    }
                }
            }
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        openMenuEntry.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {

            @Override
            public void menuEntryActivated(JPanel pnl) {
                new OpenPanelDrawer().draw(pnl);
            }
        });
        addMenuEntry(openMenuEntry);
        RibbonApplicationMenuEntryPrimary saveMenuEntry = new RibbonApplicationMenuEntryPrimary(
                RibbonUtils.getRibbonIcon("icons/filesave.png", 100, 100), LabelGrabber.INSTANCE.getLabel("save.schedule.button"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ScheduleSaver().saveSchedule(false);
            }
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        saveMenuEntry.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {

            @Override
            public void menuEntryActivated(JPanel pnl) {
                new SavePanelDrawer().draw(pnl);
            }
        });
        addMenuEntry(saveMenuEntry);
        RibbonApplicationMenuEntryPrimary saveAsMenuEntry = new RibbonApplicationMenuEntryPrimary(
                RibbonUtils.getRibbonIcon("icons/filesaveas.png", 100, 100), LabelGrabber.INSTANCE.getLabel("save.as.schedule.button"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ScheduleSaver().saveSchedule(true);
            }
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        saveAsMenuEntry.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {

            @Override
            public void menuEntryActivated(JPanel pnl) {
                new SaveAsPanelDrawer().draw(pnl);
            }
        });
        addMenuEntry(saveAsMenuEntry);
        RibbonApplicationMenuEntryPrimary printMenuEntry = new RibbonApplicationMenuEntryPrimary(
                RibbonUtils.getRibbonIcon("icons/fileprint.png", 100, 100), LabelGrabber.INSTANCE.getLabel("print.schedule.button"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Printer.getInstance().print(Application.get().getMainWindow().getMainPanel().getSchedulePanel().getScheduleList().getSchedule());
            }
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        printMenuEntry.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {

            @Override
            public void menuEntryActivated(JPanel pnl) {
                new PrintPanelDrawer().draw(pnl);
            }
        });
        addMenuEntry(printMenuEntry);
        RibbonApplicationMenuEntryPrimary optionsMenuEntry = new RibbonApplicationMenuEntryPrimary(
                RibbonUtils.getRibbonIcon("icons/options.png", 100, 100), LabelGrabber.INSTANCE.getLabel("options.button"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                optionsDialog.setVisible(true);
            }
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        optionsMenuEntry.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {

            @Override
            public void menuEntryActivated(JPanel pnl) {
                new OptionsPanelDrawer().draw(pnl);
            }
        });
        addMenuEntry(optionsMenuEntry);
        RibbonApplicationMenuEntryPrimary exitMenuEntry = new RibbonApplicationMenuEntryPrimary(
                RibbonUtils.getRibbonIcon("icons/exit.png", 100, 100), LabelGrabber.INSTANCE.getLabel("exit.button"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }, JCommandButton.CommandButtonKind.ACTION_ONLY);
        exitMenuEntry.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {

            @Override
            public void menuEntryActivated(JPanel pnl) {
                new ExitPanelDrawer().draw(pnl);
            }
        });
        addMenuEntry(exitMenuEntry);
    }

    /**
     * Confirm whether it's ok to clear the current schedule.
     * @return true if this is ok, false otherwise.
     */
    private boolean confirmClear() {
        MainPanel mainpanel = Application.get().getMainWindow().getMainPanel();
        if (mainpanel.getSchedulePanel().getScheduleList().isEmpty()) {
            return true;
        }
        int result = JOptionPane.showConfirmDialog(Application.get().getMainWindow(), LabelGrabber.INSTANCE.getLabel("schedule.clear.text"), LabelGrabber.INSTANCE.getLabel("confirm.label"), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
        if (result == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }
}
