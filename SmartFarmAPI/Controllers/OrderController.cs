using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace SmartFarmAPI.Controllers
{
    public class OrderController : ApiController
    {
        [HttpGet]
        public List<MixOrder> GetOrders(int pumpId, string from, string to)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                var fromArray = from.Split('/');
                var toArray = to.Split('/');

                return context.MixOrders.Where
                    (x => x.PumpId == pumpId 
                    && x.StartTime.Value.Date >= 
                    new DateTime(int.Parse(fromArray[2]), int.Parse(fromArray[1]), int.Parse(fromArray[0]))
                    && x.EndTime.Value.Date <= 
                    new DateTime(int.Parse(toArray[2]), int.Parse(toArray[1]), int.Parse(toArray[0]))).ToList();
            }
        }
    }
}
