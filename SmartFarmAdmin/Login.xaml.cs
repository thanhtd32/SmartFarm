using SmartFarmAPI;
using SmartFarmManagement.View;
using SmartFarmManagement.ViewModel;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace SmartFarmManagement
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class Login : Window
    {
        public Login()
        {
            InitializeComponent();
            Loaded += Login_Loaded;
        }

        private void Login_Loaded(object sender, RoutedEventArgs e)
        {
            if (Debugger.IsAttached)
            {
                txtUserName.Text = "john";
                txtPassword.Password = "123456";
            }
            else
            {
                txtUserName.Text = "";
                txtPassword.Password = "";
            }
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            var user = txtUserName.Text;
            var pass = txtPassword.Password;
            if (string.IsNullOrEmpty(user) || string.IsNullOrEmpty(pass))
            {
                MessageBox.Show("Username and password cannot be empty", "Message", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            Employee emp = null;
            using (var context = new SmartFarmDBDataContext(Constant.Path.Connection_String))
            {
                emp = context.Employees.FirstOrDefault(x => x.UserName == user && x.Password == SmartFarmAPI.Utilities.Encode.MD5Hash(pass));
                if (emp == null)
                {
                    MessageBox.Show("Invalid Username or password", "Message", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }
            }

            this.Hide();
            MainView view = new MainView();
            view.DataContext = new MainViewViewModel(emp);
            view.ShowDialog();
            this.Show();
        }
    }
}
