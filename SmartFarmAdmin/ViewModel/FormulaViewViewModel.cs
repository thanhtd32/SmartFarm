using SmartFarmAPI;
using SmartFarmManagement.Model;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartFarmManagement.ViewModel
{
    public class FormulaViewViewModel : BaseViewModel
    {
        public FormulaViewViewModel()
        {
            LoadData();
        }

        private void LoadData()
        {
            using (var context = new SmartFarmDBDataContext(Constant.Path.Connection_String))
            {
                var formulaJoin = from f in context.Formulas
                                  join p in context.Pumps
                                  on f.PumpId equals p.Id
                                  select new
                                  {
                                      PumpId = f.PumpId,
                                      PumpName = p.Name,
                                      Code = f.Code,
                                      Name = f.Name,
                                      RatioOfWater = f.RatioOfWater,
                                      RatioOfLiquidOne = f.RatioOfLiquidOne,
                                      RatioOfLiquidTwo = f.RatioOfLiquidTwo,
                                      RatioOfLiquidThree = f.RatioOfLiquidThree,
                                      RatioOfLiquidFour = f.RatioOfLiquidFour
                                  };

                Formulas = new ObservableCollection<FormulaModel>();
                foreach (var item in formulaJoin)
                {
                    Formulas.Add(new FormulaModel()
                    {
                        PumpId = item.PumpId,
                        PumpName = item.PumpName,
                        Code = item.Code,
                        Name = item.Name,
                        RatioOfWater = item.RatioOfWater,
                        RatioOfLiquidOne = item.RatioOfLiquidOne,
                        RatioOfLiquidTwo = item.RatioOfLiquidTwo,
                        RatioOfLiquidThree = item.RatioOfLiquidThree,
                        RatioOfLiquidFour = item.RatioOfLiquidFour
                    });
                }


                Pumps = new ObservableCollection<Pump>(context.Pumps.ToList());
            }
        }

        private ObservableCollection<FormulaModel> _formulas;

        public ObservableCollection<FormulaModel> Formulas
        {
            get { return _formulas; }
            set { _formulas = value; OnPropertyChanged(); }
        }

        private ObservableCollection<Pump> _pumps;

        public ObservableCollection<Pump> Pumps
        {
            get { return _pumps; }
            set { _pumps = value; OnPropertyChanged(); }
        }
    }
}
