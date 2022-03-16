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
    /// Interaction logic for MQTTSettingView.xaml
    /// </summary>
    public partial class MQTTSettingView : UserControl
    {
        public MQTTSettingView()
        {
            InitializeComponent();
            Loaded += MQTTSettingView_Loaded;
        }

        private void MQTTSettingView_Loaded(object sender, RoutedEventArgs e)
        {
            this.DataContext = new MQTTSettingViewViewModel();
        }
    }
}
