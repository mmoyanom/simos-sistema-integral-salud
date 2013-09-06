Public Class EncuestaSenderObject

    Private _encuesta As Encuestado
    Public Property encuesta() As Encuestado
        Get
            Return _encuesta
        End Get
        Set(ByVal value As Encuestado)
            _encuesta = value
        End Set
    End Property


    Private _respuestas As List(Of Respuesta)
    Public Property respuestas() As List(Of Respuesta)
        Get
            Return _respuestas
        End Get
        Set(ByVal value As List(Of Respuesta))
            _respuestas = value
        End Set
    End Property



End Class
