Public Class Respuesta

    Private _respuestaId As Integer
    Public Property RespuestaId() As Integer
        Get
            Return _respuestaId
        End Get
        Set(ByVal value As Integer)
            _respuestaId = value
        End Set
    End Property


    Private _preguntaId As Integer
    Public Property PreguntaId() As Integer
        Get
            Return _preguntaId
        End Get
        Set(ByVal value As Integer)
            _preguntaId = value
        End Set
    End Property


    Private _descripcion As String
    Public Property Descripcion() As String
        Get
            Return _descripcion
        End Get
        Set(ByVal value As String)
            _descripcion = value
        End Set
    End Property



End Class
