using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace SmartFarmAPI.Controllers
{
    public class EmployeeController : ApiController
    {

        [HttpGet]
        public List<Employee> GetEmployees()
        {
            using (var context = new SmartFarmDBDataContext())
            {
                return context.Employees.ToList();
            }
        }


        [HttpGet]
        public Employee GetEmployeeByCode(int id)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                return context.Employees.FirstOrDefault(emp => emp.Id == id);
            }
        }

        [HttpGet]
        public Employee GetEmployeeByCode(string code)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                return context.Employees.FirstOrDefault(emp => emp.Code == code);
            }
        }

        [HttpGet]
        public Employee GetEmployeeByUserAndPass(string user, string pass)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                return context.Employees.FirstOrDefault(emp => emp.UserName == user && emp.Password == Utilities.Encode.MD5Hash(pass));
            }
        }

        [HttpPut]
        public bool PutEmployeeChangePass(string user, string pass, string newPass)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                var employee = context.Employees.FirstOrDefault(emp => emp.UserName == user && emp.Password == Utilities.Encode.MD5Hash(pass));

                if (employee == null)
                {
                    return false;
                }
                else
                {
                    employee.Password = Utilities.Encode.MD5Hash(newPass);
                    context.SubmitChanges();
                    return true;
                }
            }
        }


        [HttpPost]
        public Employee PostEmployee(Employee emp)
        {
            using (var context = new SmartFarmDBDataContext())
            {
                var employee = new Employee()
                {
                    Code = emp.Code,
                    Image = emp.Image,
                    FirstName = emp.FirstName,
                    LastName = emp.LastName,
                    MiddleName = emp.MiddleName,
                    BirthDay = emp.BirthDay,
                    Email = emp.Email,
                    Phone = emp.Phone,
                    IdentityCard = emp.IdentityCard,
                    Address = emp.Address,
                    UserName = emp.UserName,
                    Password = Utilities.Encode.MD5Hash(emp.Password)
                };

                context.Employees.InsertOnSubmit(employee);
                context.SubmitChanges();

                return employee;
            }
        }
    }
}
