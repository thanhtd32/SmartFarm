using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MQTTServer.Constant
{
    public static class Topic
    {
        public static string TOPIC_FORMULA = "/uel/farm/run";
        public static string TOPIC_FORMULA_DEGREE_PH = "/uel/farm/degreeph";
        public static string TOPIC_FORMULA_DEGREE_EC = "/uel/farm/degreeec";
        public static string TOPIC_FORMULA_PRESSURE = "/uel/farm/pressure";
        public static string TOPIC_FORMULA_PUMP_STATUS = "/uel/farm/pump/status";
    }
}
