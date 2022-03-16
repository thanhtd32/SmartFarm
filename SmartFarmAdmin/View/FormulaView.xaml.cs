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
    /// Interaction logic for FormulaView.xaml
    /// </summary>
    public partial class FormulaView : UserControl
    {
        public FormulaView()
        {
            InitializeComponent();
            Loaded += FormulaView_Loaded;
        }

        private void FormulaView_Loaded(object sender, RoutedEventArgs e)
        {
            this.DataContext = new FormulaViewViewModel();
        }
    }
}
