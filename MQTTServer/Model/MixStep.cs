using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MQTTServer.Model
{
    public class MixStep
    {
        public int formulaId { get; set; }
        public string liquid { get; set; }
        public int pumpId { get; set; }
        public double quantity { get; set; }
        public double speed { get; set; }
        public bool status { get; set; }
        public double valueWater { get; set; }
        public double valueOne { get; set; }
        public double valueTwo { get; set; }
        public double valueThree { get; set; }
        public double valueFour { get; set; }
    }
}
