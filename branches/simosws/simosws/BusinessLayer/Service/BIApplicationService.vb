Imports simosws.DTO

Namespace Service
    Public Interface BIApplicationService

        Function GetRespuestas() As List(Of Respuesta)
        Function GetAsignaciones(ByVal usuarioId As String, ByVal fecha As String) As List(Of Asignacion)
        Function GetDocumentTypes() As List(Of DocumentType)
        Function GetPaymentConcepts() As List(Of PaymentConcept)
        Function GetLocations() As List(Of Location)
        Function GetDeliveryModes() As List(Of DeliveryMode)
        Function GetStaffIndicatingPayments() As List(Of StaffIndicatingPayment)
        Function SetRespuestasEncuestas(ByVal rptasEncs As List(Of RespuestaEncuesta)) As Integer
        Function SetRespuestaEncuesta(ByVal rptaEnc As RespuestaEncuesta) As Integer



    End Interface
End Namespace

