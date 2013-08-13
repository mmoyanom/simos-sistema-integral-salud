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
    End Interface
End Namespace

