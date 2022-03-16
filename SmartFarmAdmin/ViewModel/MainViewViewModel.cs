using SmartFarmAPI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Media.Imaging;

namespace SmartFarmManagement.ViewModel
{
    public class MainViewViewModel : BaseViewModel
    {
        public MainViewViewModel(Employee employee)
        {
            LoadInfo(employee);
        }

       

        #region Property

        private BitmapImage _image;
        public BitmapImage Image { get => _image; set { _image = value; OnPropertyChanged(); } }

        private string _code;
        public string Code { get => _code; set { _code = value; OnPropertyChanged(); } }

        private string _name;
        public string Name { get => _name; set { _name = value; OnPropertyChanged(); } }
        #endregion

        private void LoadInfo(Employee employee)
        {
            Image = Utilities.ImageConvert.ConvertBase64ToBitmapImage(employee.Image);
            Code = employee.Code;
            Name = $"{employee.LastName} {employee.FirstName}";
        }
    }
}
