using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MQTTServer.Constant
{
    public static class Topic
    {
        public static string TOPIC_FORMULA = "/uit/farm/run";
        public static string TOPIC_FORMULA_DEGREE_PH = "/uit/farm/degreeph";
        public static string TOPIC_FORMULA_DEGREE_EC = "/uit/farm/degreeec";
        public static string TOPIC_FORMULA_PRESSURE = "/uit/farm/pressure";
        public static string TOPIC_FORMULA_PUMP_STATUS = "/uit/farm/pump/status";
    }
}
