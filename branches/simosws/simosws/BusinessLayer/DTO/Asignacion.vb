Public Class Asignacion


    Private _encuestaId As Integer
    Public Property EncuestaId() As Integer
        Get
            Return _encuestaId
        End Get
        Set(ByVal value As Integer)
            _encuestaId = value
        End Set
    End Property


    Private _establecimientoId As String
    Public Property EstablecimientoId() As String
        Get
            Return _establecimientoId
        End Get
        Set(ByVal value As String)
            _establecimientoId = value
        End Set
    End Property


    Private _establecimientoName As String
    Public Property EstablecimientoName() As String
        Get
            Return _establecimientoName
        End Get
        Set(ByVal value As String)
            _establecimientoName = value
        End Set
    End Property


    Private _fechaProgramacion As String
    Public Property FechaProgramacion() As String
        Get
            Return _fechaProgramacion
        End Get
        Set(ByVal value As String)
            _fechaProgramacion = value
        End Set
    End Property


    Private _turnoId As Integer
    Public Property TurnoId() As Integer
        Get
            Return _turnoId
        End Get
        Set(ByVal value As Integer)
            _turnoId = value
        End Set
    End Property


    Private _turnoDescripcion As String
    Public Property TurnoDescripcion() As String
        Get
            Return _turnoDescripcion
        End Get
        Set(ByVal value As String)
            _turnoDescripcion = value
        End Set
    End Property


    Private _fechaProgramada As String
    Public Property FechaProgramada() As String
        Get
            Return _fechaProgramada
        End Get
        Set(ByVal value As String)
            _fechaProgramada = value
        End Set
    End Property


End Class
