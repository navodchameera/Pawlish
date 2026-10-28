package controller;

import dao.SettingsDAO;
import model.User;
import util.SessionManager;
import views.PanelSettings;
import javax.swing.JOptionPane;

public class ControllerSettings
{
    private final PanelSettings view;
    private final SettingsDAO settingsDAO;

    public ControllerSettings(PanelSettings view)
    {
        this.view = view;
        this.settingsDAO = new SettingsDAO();

        view.addSaveUsernameListener(e -> saveUsername());
        view.addSavePasswordListener(e -> savePassword());
        view.addClearPasswordListener(e -> view.clearPasswords());
    }

    public void refresh()
    {
        User user = SessionManager.getCurrentUser();
        view.clearPasswords();

        if (user == null)
        {
            view.showAccount("Not signed in", "");
            return;
        }

        view.showAccount(user.getFirstName() + " " + user.getLastName(), user.getUsername());
    }

    private void saveUsername()
    {
        User user = SessionManager.getCurrentUser();

        if (user == null)
        {
            JOptionPane.showMessageDialog(view, "Please sign in first");
            return;
        }

        String username = view.getUsername();

        if (username.isEmpty())
        {
            JOptionPane.showMessageDialog(view, "Please enter a username");
            return;
        }

        if (username.equals(user.getUsername()))
        {
            JOptionPane.showMessageDialog(view, "Please enter a different username");
            return;
        }

        if (settingsDAO.updateUsername(user.getId(), username))
        {
            //updating current session after database saved
            user.setUsername(username);
            view.showAccount(user.getFirstName() + " " + user.getLastName(), username);
            JOptionPane.showMessageDialog(view, "Username updated. Use this username next time you sign in.");
        }
    }

    private void savePassword()
    {
        User user = SessionManager.getCurrentUser();

        if (user == null)
        {
            JOptionPane.showMessageDialog(view, "Please sign in first");
            return;
        }

        String currentPassword = view.getCurrentPassword();
        String newPassword = view.getNewPassword();
        String confirmPassword = view.getConfirmPassword();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty())
        {
            JOptionPane.showMessageDialog(view, "Please fill all three password fields");
            return;
        }

        if (!newPassword.equals(confirmPassword))
        {
            JOptionPane.showMessageDialog(view, "New passwords do not match");
            return;
        }

        //login currently trims passwords so outer spaces cannot be used
        if (!newPassword.equals(newPassword.trim()))
        {
            JOptionPane.showMessageDialog(view, "Password cannot start or end with spaces");
            return;
        }

        if (newPassword.equals(currentPassword))
        {
            JOptionPane.showMessageDialog(view, "New password must be different from current password");
            return;
        }

        if (settingsDAO.updatePassword(user.getId(), currentPassword, newPassword))
        {
            //updating session only after password saved
            user.setPassword(newPassword);
            view.clearPasswords();
            JOptionPane.showMessageDialog(view, "Password updated successfully");
        }
    }
}
