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
        Dim listrptasEncs As New List(Of Respuesta)
        If SrptasEncs IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            listrptasEncs = slz.Deserialize(Of List(Of Respuesta))(SrptasEncs)
            resul = service.SetRespuestasEncuestas(listrptasEncs)
        Else
            resul = 0
        End If

        Return resul
    End Function

    <WebMethod()> _
    <ScriptMethod(UseHttpGet:=True)> _
    Public Function SetRespuestaEncuesta(ByVal SrptaEnc As String)
        Dim result As Integer = 0
        Dim service As New MSSQLApplicationServiceImpl
        Dim rptaEnc As New EncuestaSenderObject
        If SrptaEnc IsNot Nothing Then
            Dim slz As New JavaScriptSerializer
            rptaEnc = slz.Deserialize(Of EncuestaSenderObject)(SrptaEnc)
            result = service.SaveEncuesta(rptaEnc.encuesta)

            For Each r As Respuesta In rptaEnc.respuestas
                r.encuestadoId = result
                If r.preguntaId = 7 Then
                    Dim id = service.SetRespuestaEncuesta(r)
                    Dim child = r.child
                    For Each rx As Respuesta In child
                        rx.respuestaParentId = id
                        rx.encuestadoId = result
                        service.SetRespuestaEncuesta(rx)
                    Next
                ElseIf r.preguntaId = 21 Then
                    Dim id = service.SetRespuestaEncuesta(r)
                    Dim child = r.child
                    For Each rx As Respuesta In child
                        If rx.preguntaId = 23 Then
                            rx.respuestaParentId = id
                            rx.encuestadoId = result
                            Dim idrx = service.SetRespuestaEncuesta(rx)
                            Dim childrx = rx.child
                            For Each rxy As Respuesta In childrx
                                rxy.respuestaParentId = idrx
                                rxy.encuestadoId = result
                                service.SetRespuestaEncuesta(rxy)
                            Next
                        End If
                    Next
                Else
                    service.SetRespuestaEncuesta(r)
                End If

            Next
        Else
            result = 0
        End If

        Return result
    End Function
End Class


