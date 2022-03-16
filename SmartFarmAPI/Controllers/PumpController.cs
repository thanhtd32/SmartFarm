using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace SmartFarmAPI.Controllers
{
    public class PumpController : ApiController
    {
        [HttpGet]
        public List<Pump> GetPumps()
        {
            using (var context = new SmartFarmDBDataContext())
            {
                return context.Pumps.ToList();
            }
        }

        [HttpGet]
        public Pump GetPumpById(int id)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                return context.Pumps.FirstOrDefault(x => x.Id == id);
            }
        }
    }
}
