# MFQ Simulation
This is a simulation of how a Multilevel feedback queue works to schedule processes. 
The purpose of this project was to learn different scheduling schema and since 
MFQ also implements round robin scheduling at it's base level, it was a good teaching tool.
This program includes full documentation using Javadoc.

## Table of contents
* [General info](#general-info)
* [Screenshots](#screenshots)
* [Technologies](#technologies)
* [Status](#status)
* [Inspiration](#inspiration)
* [Contact](#contact)

## General info
Multilevel feedback queue scheduling allows processes to move between different queues. 
If a process uses too much CPU time, it will be moved to a lower-priority queue.
In a real scenario, this scheme leaves I/O-bound and interactive processes in the higher priority queues.
Since this is a simulation, the processes are given different CPU times initially to dictate which processes are preempted.
In addition, a process that waits too long in a lower-priority queue may be moved to a higher priority queue.
This is all to prevent starvation of certain lower priority processes.

## Screenshots
TODO: add screenshots 
![Example screenshot](./img/screenshot.png)

## Technologies
* Made in BlueJ with Java
* Contains full documentation via javadoc

## Status
Project is: _finished_

## Inspiration
I was inspired while learning about process scheduling to implement a simulation of MFQs to challenge myself and learn more.

## Contact
Created by [@josherz94] - feel free to contact me!
