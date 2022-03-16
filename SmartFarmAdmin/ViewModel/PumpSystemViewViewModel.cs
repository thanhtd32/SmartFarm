using SmartFarmAPI;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartFarmManagement.ViewModel
{
    public class PumpSystemViewViewModel : BaseViewModel
    {
        public PumpSystemViewViewModel()
        {
            LoadData();
        }

        private void LoadData()
        {
            using (var context = new SmartFarmDBDataContext(Constant.Path.Connection_String))
            {
                Pumps = new ObservableCollection<Pump>(context.Pumps.ToList());
            }
        }

        private ObservableCollection<Pump> _pumps;

        public ObservableCollection<Pump> Pumps
        {
            get { return _pumps; }
            set { _pumps = value; OnPropertyChanged(); }
        }

        private Pump _pumpSelected;

        public Pump PumpSelected
        {
            get { return _pumpSelected; }
            set
            {
                _pumpSelected = value; OnPropertyChanged();
                if (value != null)
                {
                    Pump = new Pump()
                    {
                        Id = value.Id,
                        Code = value.Code,
                        Name = value.Name,
                        Image = value.Image
                    };
                }
                else
                {
                    Pump = new Pump();
                }
            }
        }

        private Pump _pump;

        public Pump Pump
        {
            get { return _pump; }
            set { _pump = value; OnPropertyChanged(); }
        }

    }
}
