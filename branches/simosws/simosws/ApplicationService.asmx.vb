Imports System.Web.Services
Imports System.Web.Services.Protocols
Imports System.ComponentModel
Imports simosws.Service.Impl
Imports System.Web.Script.Serialization
Imports System.Web.Script.Services
Imports System.Xml.Serialization


<System.Web.Services.WebService(Namespace:="http://simosws.com/")> _
<System.Web.Services.WebServiceBinding(ConformsTo:=WsiProfiles.BasicProfile1_1)> _
<ToolboxItem(False)> _
Public Class ApplicationService
    Inherits System.Web.Services.WebService

    <WebMethod()> _
    <ScriptMethod(UseHttpGet:=True)> _
    Public Function GetLocations() As String

        util.Log.Append("entrando en GetLocations()")
        Dim response As String
        Dim service As New MSSQLApplicationServiceImpl
        Dim x As New JsonObjectResponse
        Dim items = service.GetLocations()
        If items IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            response = slz.Serialize(items)
        Else
            response = "Error"
        End If

        Return response
    End Function

    <WebMethod()> _
    <ScriptMethod(UseHttpGet:=True)> _
    Public Function GetDocumentTypes() As JsonObjectResponse
        Dim response As String
        Dim service As New MSSQLApplicationServiceImpl
        Dim x As New JsonObjectResponse
        Dim items = service.GetDocumentTypes()
        If items IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            response = slz.Serialize(items)
            x.value = response
        Else
            response = "Error"
        End If

        Return x
    End Function

    <WebMethod()> _
    <ScriptMethod(UseHttpGet:=True)> _
    Public Function GetPaymentConcepts() As JsonObjectResponse
        Dim response As String
        Dim service As New MSSQLApplicationServiceImpl
        Dim x As New JsonObjectResponse
        Dim items = service.GetPaymentConcepts()
        If items IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            response = slz.Serialize(items)
            x.value = response
        Else
            response = "Error"
        End If

        Return x
    End Function

    <WebMethod()> _
    <ScriptMethod(UseHttpGet:=True)> _
    Public Function GetDeliveryModes() As JsonObjectResponse
        Dim response As String
        Dim service As New MSSQLApplicationServiceImpl
        Dim x As New JsonObjectResponse
        Dim items = service.GetDeliveryModes()
        If items IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            response = slz.Serialize(items)
            x.value = response
        Else
            response = "Error"
        End If

        Return x
    End Function

    <WebMethod()> _
    <ScriptMethod(UseHttpGet:=True)> _
    Public Function GetStaffIndicatingPayment() As JsonObjectResponse
        Dim response As String
        Dim service As New MSSQLApplicationServiceImpl
        Dim x As New JsonObjectResponse
        Dim items = service.GetStaffIndicatingPayments()
        If items IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            response = slz.Serialize(items)
            x.value = response
        Else
            response = "Error"
        End If

        Return x
    End Function

    <WebMethod()> _
    <ScriptMethod(UseHttpGet:=True)> _
    Public Function GetRespuestas() As JsonObjectResponse
        Dim response As String
        Dim service As New MSSQLApplicationServiceImpl
        Dim x As New JsonObjectResponse
        Dim items = service.GetRespuestas()
        If items IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            response = slz.Serialize(items)
            x.value = response
        Else
            response = "Error"
        End If

        Return x
    End Function

    <WebMethod()> _
    <ScriptMethod(UseHttpGet:=True)> _
    Public Function GetAsignaciones(ByVal UsuarioId As Integer, ByVal fecha As String) As JsonObjectResponse
        Dim response As String
        Dim service As New MSSQLApplicationServiceImpl
        Dim x As New JsonObjectResponse
        Dim items = service.GetAsignaciones(UsuarioId, fecha)
        If items IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            response = slz.Serialize(items)
            x.value = response
        Else
            response = "Error"
        End If

        Return x
    End Function
    <WebMethod()> _
    <ScriptMethod(UseHttpGet:=True)> _
    Public Function SetRespuestasEncuestas(ByVal SrptasEncs As String)
        Dim resul As Integer = 0
        Dim service As New MSSQLApplicationServiceImpl
        Dim listrptasEncs As New List(Of RespuestaEncuesta)
        If SrptasEncs IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            listrptasEncs = slz.Deserialize(Of List(Of RespuestaEncuesta))(SrptasEncs)
            resul = service.SetRespuestasEncuestas(listrptasEncs)
        Else
            resul = 0
        End If

        Return resul
    End Function
End Class


