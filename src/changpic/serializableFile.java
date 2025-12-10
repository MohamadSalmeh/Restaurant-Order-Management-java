package changpic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class serializableFile {

    public static void saveClientsDataOnFile(ArrayList<Clients> clientInfo, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path)))) {
            oos.writeObject(clientInfo);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "FileInfoNotFound", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Write From File Wrong ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void saveMealsDataOnFile(ArrayList<Meals> MealsInfo, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path)))) {
            oos.writeObject(MealsInfo);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "FileInfoNotFound", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Write From File Wrong ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static ArrayList<Clients> getClientsDataFomeFile(String path) {
        ArrayList<Clients> ClientFileInfo = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)))) {
            ClientFileInfo = (ArrayList<Clients>) ois.readObject();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "FileInfoNotFound", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Read From File Wrong ", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Information Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return ClientFileInfo;
    }

    public static ArrayList<Meals> getMealsFataFomeFile(String path) {
        ArrayList<Meals> MealsFileInfo = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)))) {
            MealsFileInfo = (ArrayList<Meals>) ois.readObject();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "FileInfoNotFound", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Read From File Wrong ", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Information Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return MealsFileInfo;
    }

}
