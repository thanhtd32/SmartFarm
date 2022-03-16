using SmartFarmManagement.ViewModel;
using System;
using System.Collections.Generic;
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

namespace SmartFarmManagement.View
{
    /// <summary>
    /// Interaction logic for MainView.xaml
    /// </summary>
    public partial class MainView : Window
    {
        public MainView()
        {
            InitializeComponent();
        }

        private void btnPumpSystem_Click(object sender, RoutedEventArgs e)
        {
            PumpSystemView pump = new PumpSystemView();
            frameView.NavigationUIVisibility = NavigationUIVisibility.Hidden;
            frameView.Navigate(new System.Uri("View/PumpSystemView.xaml", UriKind.RelativeOrAbsolute));
        }

        private void btnFormula_Click(object sender, RoutedEventArgs e)
        {
            FormulaView formula = new FormulaView();
            frameView.NavigationUIVisibility = NavigationUIVisibility.Hidden;
            frameView.Navigate(new System.Uri("View/FormulaView.xaml", UriKind.RelativeOrAbsolute));
        }

        private void btnDevice_Click(object sender, RoutedEventArgs e)
        {
            DeviceView device = new DeviceView();
            frameView.NavigationUIVisibility = NavigationUIVisibility.Hidden;
            frameView.Navigate(new System.Uri("View/DeviceView.xaml", UriKind.RelativeOrAbsolute));
        }

        private void btnEmployee_Click(object sender, RoutedEventArgs e)
        {
            EmployeeView employee = new EmployeeView();
            frameView.NavigationUIVisibility = NavigationUIVisibility.Hidden;
            frameView.Navigate(new System.Uri("View/EmployeeView.xaml", UriKind.RelativeOrAbsolute));
        }

        private void btnMqtt_Click(object sender, RoutedEventArgs e)
        {
            MQTTSettingView mqtt = new MQTTSettingView();
            frameView.NavigationUIVisibility = NavigationUIVisibility.Hidden;
            frameView.Navigate(new System.Uri("View/MQTTSettingView.xaml", UriKind.RelativeOrAbsolute));
        }
    }
}
