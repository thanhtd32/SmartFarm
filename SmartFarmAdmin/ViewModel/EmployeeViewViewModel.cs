using SmartFarmAPI;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartFarmManagement.ViewModel
{
    public class EmployeeViewViewModel : BaseViewModel
    {
        public EmployeeViewViewModel()
        {
            LoadData();
        }

        private void LoadData()
        {
            using (var context = new SmartFarmDBDataContext(Constant.Path.Connection_String))
            {
                Employees = new ObservableCollection<Employee>(context.Employees.ToList());
            }
        }

        private ObservableCollection<Employee> _employees;

        public ObservableCollection<Employee> Employees
        {
            get { return _employees; }
            set { _employees = value; OnPropertyChanged(); }
        }
    }
}
