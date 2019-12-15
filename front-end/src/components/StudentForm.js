import React from 'react';

const StudentForm = (props) => (
    <div className="col-sm-6">
        <div className="row">
            <div className="col-md-4">
                <input onBlur={(event)=>props.onBlur(event)} className="form-control" type="text" placeholder="University" name="university"></input>
            </div>
            <div className="col-md-4">
                <input onBlur={(event)=>props.onBlur(event)} className="form-control" type="text" placeholder="Name" name="name"></input>
            </div>
            <div className="col-md-4">
                <input onBlur={(event)=>props.onBlur(event)} className="form-control" type="number" placeholder="Enrollment year" name="enrollmentYear"></input>
            </div>
        </div>
        <div className="row">
            <div className="col-md-4">
                <input onBlur={(event)=>props.onBlur(event)} className="form-control" type="text" placeholder="Faculty" name="faculty"></input>
            </div>
            <div className="col-md-4">
                <input onBlur={(event)=>props.onBlur(event)} className="form-control" type="text" placeholder="Surname" name="surname"></input>
            </div>
            <div className="col-md-4">
                <input onBlur={(event)=>props.onBlur(event)} className="form-control" type="number" placeholder="Student ID" name="studentId"></input>
            </div>
        </div>
    </div>
);

export default StudentForm;