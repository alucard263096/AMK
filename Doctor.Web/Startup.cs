using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(Doctor.Web.Startup))]
namespace Doctor.Web
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
