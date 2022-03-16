using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Media.Imaging;

namespace SmartFarmManagement.Utilities
{
    public static class ImageConvert
    {
        public static BitmapImage ConvertBase64ToBitmapImage(string base64)
        {
            if (string.IsNullOrEmpty(base64))
            {
                return null;
            }

            byte[] binaryData = System.Convert.FromBase64String(base64);

            BitmapImage bi = new BitmapImage();
            bi.BeginInit();
            bi.StreamSource = new MemoryStream(binaryData);
            bi.EndInit();
            bi.Freeze();

            return bi;
        }
    }
}
