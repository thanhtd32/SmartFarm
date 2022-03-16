using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace SmartFarmAPI.Controllers
{
    public class FormulaController : ApiController
    {
        [HttpGet]
        public List<Formula> GetFormulaByPump(int id)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                return context.Formulas.Where(pump => pump.PumpId == id).ToList();
            }
        }

        [HttpGet]
        public bool GetFormulaByName(string name)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                var formula = context.Formulas.FirstOrDefault(pump => pump.Name.ToLower() == name.ToLower());
                if (formula != null)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        [HttpPost]
        public Formula PostFormula(Formula formula)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                var formulaPost = new Formula()
                {
                    PumpId = formula.PumpId,
                    Code = formula.Code,
                    Name = formula.Name,
                    RatioOfWater = formula.RatioOfWater,
                    RatioOfLiquidOne = formula.RatioOfLiquidOne,
                    RatioOfLiquidTwo = formula.RatioOfLiquidTwo,
                    RatioOfLiquidThree = formula.RatioOfLiquidThree,
                    RatioOfLiquidFour = formula.RatioOfLiquidFour,
                    DateCreated = DateTime.Now,
                    DateUpdated = DateTime.Now
                };

                context.Formulas.InsertOnSubmit(formulaPost);
                context.SubmitChanges();

                return formulaPost;
            }
        }

        [HttpPut]
        public Formula PutFormula(Formula formula)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                var formulaPut = context.Formulas.FirstOrDefault(x => x.Id == formula.Id);
                if (formulaPut != null)
                {
                    formulaPut.Name = formulaPut.Code = formula.Name;
                    formulaPut.RatioOfWater = formula.RatioOfWater;
                    formulaPut.RatioOfLiquidOne = formula.RatioOfLiquidOne;
                    formulaPut.RatioOfLiquidTwo = formula.RatioOfLiquidTwo;
                    formulaPut.RatioOfLiquidThree = formula.RatioOfLiquidThree;
                    formulaPut.RatioOfLiquidFour = formula.RatioOfLiquidFour;
                    formulaPut.DateUpdated = DateTime.Now;
                    context.SubmitChanges();

                    return formulaPut;
                }

                return null;
            }
        }

        [HttpDelete]
        public bool DeleteFormula(Formula formula)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                var formulaDelete = context.Formulas.FirstOrDefault(x => x.Id == formula.Id);
                if (formulaDelete != null)
                {
                    context.Formulas.DeleteOnSubmit(formulaDelete);
                    context.SubmitChanges();

                    return true;
                }

                return false;
            }
        }
    }
}
