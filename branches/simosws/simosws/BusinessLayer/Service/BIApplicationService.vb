Imports simosws.DTO

Namespace Service
    Public Interface BIApplicationService

        Function GetRespuestas() As List(Of OpcionRespuesta)
        Function GetAsignaciones(ByVal usuarioId As String, ByVal fecha As String) As List(Of Asignacion)
        Function GetDocumentTypes() As List(Of DocumentType)
        Function GetPaymentConcepts() As List(Of PaymentConcept)
        Function GetLocations() As List(Of Location)
        Function GetDeliveryModes() As List(Of DeliveryMode)
        Function GetStaffIndicatingPayments() As List(Of StaffIndicatingPayment)

        Function SaveEncuesta(ByVal encuesta As Encuestado) As Integer

        Function SetRespuestasEncuestas(ByVal rptasEncs As List(Of Respuesta)) As Integer
        Function SetRespuestaEncuesta(ByVal rptaEnc As Respuesta) As Integer



    End Interface
End Namespace

