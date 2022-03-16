using MQTTServer.Model;
using Newtonsoft.Json;
using SmartFarmAPI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using uPLibrary.Networking.M2Mqtt;
using uPLibrary.Networking.M2Mqtt.Messages;


namespace MQTTServer
{
    class Program
    {
        [Obsolete]
        static void Main(string[] args)
        {

            try
            {
                MqttClient client = new MqttClient(IPAddress.Parse(Constant.MQTT.IP),
                                                Constant.MQTT.PORT,
                                                false,
                                                new System.Security.Cryptography.X509Certificates.X509Certificate(),
                                                new System.Security.Cryptography.X509Certificates.X509Certificate(),
                                                MqttSslProtocols.None);

                string clientId = Guid.NewGuid().ToString();
                client.Connect(clientId, Constant.MQTT.USER, Constant.MQTT.PASS);


                client.Subscribe(new string[] { "#" },
                    new byte[] { MqttMsgBase.QOS_LEVEL_EXACTLY_ONCE });
                client.MqttMsgPublishReceived += Client_MqttMsgPublishReceived;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

        }

        static string connectionString = "Data Source=THANHTRAN;Initial Catalog=SmartFarm;User ID=sa;Password=241213";
        static bool FirstRunTopicFormula = true;
        static bool FirstRunTopicStatus = true;
        private static void Client_MqttMsgPublishReceived(object sender, MqttMsgPublishEventArgs e)
        {
            if (e.Topic.Contains(Constant.Topic.TOPIC_FORMULA))
            {
                if (!FirstRunTopicFormula)
                {
                    try
                    {
                        string value = Encoding.UTF8.GetString(e.Message);
                        var mixStep = JsonConvert.DeserializeObject<MixStep>(value);
                        if (mixStep.status && mixStep.pumpId != 0)
                        {
                            using (var context = new SmartFarmDBDataContext(connectionString))
                            {
                                var order = new MixOrder()
                                {
                                    PumpId = mixStep.pumpId,
                                    FormulaId = mixStep.formulaId,
                                    Volume = mixStep.quantity,
                                    StartTime = DateTime.Now,
                                    Status = 0,
                                    DateCreated = DateTime.Now,
                                    DateUpdated = DateTime.Now
                                };

                                context.MixOrders.InsertOnSubmit(order);
                                context.SubmitChanges();
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine(ex.Message);
                    }
                }
                FirstRunTopicFormula = false;
            }

            if (e.Topic.Contains(Constant.Topic.TOPIC_FORMULA_PUMP_STATUS))
            {
                if (!FirstRunTopicStatus)
                {
                    try
                    {
                        string value = Encoding.UTF8.GetString(e.Message);
                        var mixStep = JsonConvert.DeserializeObject<MixStep>(value);
                        if (!mixStep.status && mixStep.pumpId != 0)
                        {
                            using (var context = new SmartFarmDBDataContext(connectionString))
                            {
                                var order = context.MixOrders.FirstOrDefault(x => x.EndTime == null && x.Status == 0 && x.PumpId == mixStep.pumpId);
                                if (order != null)
                                {
                                    if (mixStep.liquid == "a")
                                    {
                                        order.Status = 2;
                                    }
                                    else
                                    {
                                        order.Status = 1;
                                    }
                                    order.EndTime = DateTime.Now;
                                    order.DateUpdated = DateTime.Now;
                                    context.SubmitChanges();
                                }
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine(ex.Message);
                    }
                }
                FirstRunTopicStatus = false;
            }
        }
    }
}

