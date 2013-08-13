Imports System.Web.Services
Imports System.Web.Services.Protocols
Imports System.ComponentModel
Imports simosws.Service.Impl
Imports System.Web.Script.Serialization

<System.Web.Services.WebService(Namespace:="http://thisisademoofws.com/")> _
<System.Web.Services.WebServiceBinding(ConformsTo:=WsiProfiles.BasicProfile1_1)> _
<ToolboxItem(False)> _
Public Class LoginService
    Inherits System.Web.Services.WebService

    Public Function Token(ByVal tokenString As String) As String

        Return Nothing
    End Function

    <WebMethod()> _
    Public Function Login(ByVal username As String, ByVal password As String) As String
        Dim response As String
        Dim service As New MSSQLLoginServiceImpl

        Dim loginObj = service.Authenticate(username, password)
        If loginObj IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            response = slz.Serialize(loginObj)
        Else
            response = "Error"
        End If

        Return response
    End Function

End Class