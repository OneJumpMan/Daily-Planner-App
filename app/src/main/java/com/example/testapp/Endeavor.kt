package com.example.testapp

class Endeavor (
    private val _type : EndeavorType,
    private var _title : String?,
    private var _duration : DateTime.Duration?,
    private var _notes : String?
    ) {

    constructor(type : EndeavorType) : this(type, null, null, null)

    // only ever non-null for endeavors of task type
    private var _completed : Boolean?

    // only ever non-null for endeavors of appointment type
    private var _periodic : Boolean?
    private var _period : ArrayList<Boolean>?

    // non-null for tasks and appointments
    private var _scheduledTime : DateTime.Time?
    private var _scheduledDate : DateTime.Date?

    init {
        // nullable for task and appointment types
        _scheduledDate = null
        _scheduledTime = null
        _period = null


        when (_type) {
            EndeavorType.APPOINTMENT -> {
                _completed = null
                _periodic = false // always non-null for appointment types
            }
            EndeavorType.TASK -> {
                _completed = false // always non-null for task types
                _periodic = null
            }
            else -> {
                _completed = null
                _periodic = null
            }
        }
    }

    val type
        get() = _type
    var title
        get() = _title
        set(value) { _title = value}
    var duration
        get() = _duration
        set(value) { _duration = value}
    var notes
        get() = _notes
        set(value) { _notes = value}


    var completed
        get() = _completed
        set(value) {
            _completed = if (_type == EndeavorType.TASK) {value} else {null}
        }
    var scheduledDate
        get() = _scheduledDate
        set(value) {
            _scheduledDate = if (_type == EndeavorType.TASK) {value} else {null}
        }
    var scheduledTime
        get() = _scheduledTime
        set(value) {
            _scheduledTime = if (_type == EndeavorType.TASK) {value} else {null}
        }


    var periodic
        get() = _periodic
        set(value) {
            _periodic = if (_type == EndeavorType.APPOINTMENT) {value} else {null}
        }
    var period
        get() = _period
        set(value) {
            _period = if (_type == EndeavorType.APPOINTMENT) {value} else {null}
        }



    private var _markedForDeletion = false
    var markedForDeletion
        get() = _markedForDeletion
        set(value) { _markedForDeletion = value }

}


enum class EndeavorType() {
    HABIT,
    TASK,
    APPOINTMENT;
}