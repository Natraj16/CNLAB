# ==========================
#        lab2.tcl
# ==========================

# Create Simulator
set ns [new Simulator]

# Open NAM file
set nf [open lab2.nam w]
$ns namtrace-all $nf

# Open Trace file
set tf [open lab2.tr w]
$ns trace-all $tf

# ----------------------------
# Create Network Nodes
# ----------------------------
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]

# ----------------------------
# Create Links (Star Topology)
# ----------------------------
$ns duplex-link $n0 $n4 1005Mb 1ms DropTail
$ns duplex-link $n1 $n4 50Mb 1ms DropTail
$ns duplex-link $n2 $n4 2000Mb 1ms DropTail
$ns duplex-link $n3 $n4 200Mb 1ms DropTail
$ns duplex-link $n4 $n5 1Mb 1ms DropTail

# ----------------------------
# Create Ping Agents
# ----------------------------

# Ping from n0
set p1 [new Agent/Ping]
$ns attach-agent $n0 $p1
$p1 set packetSize_ 50000
$p1 set interval_ 0.0001

# Ping from n1
set p2 [new Agent/Ping]
$ns attach-agent $n1 $p2

# Ping from n2
set p3 [new Agent/Ping]
$ns attach-agent $n2 $p3
$p3 set packetSize_ 30000
$p3 set interval_ 0.00001

# Ping from n3
set p4 [new Agent/Ping]
$ns attach-agent $n3 $p4

# Ping receiver at n5
set p5 [new Agent/Ping]
$ns attach-agent $n5 $p5

# ----------------------------
# Set Queue Limits
# ----------------------------
$ns queue-limit $n0 $n4 5
$ns queue-limit $n2 $n4 3
$ns queue-limit $n4 $n5 2

# ----------------------------
# Overriding Ping recv()
# ----------------------------
Agent/Ping instproc recv {from rtt} {
    $self instvar node_
    puts "node [$node_ id] received answer from $from with round trip time $rtt msec"
}

# ----------------------------
# Connect Agents
# ----------------------------
$ns connect $p1 $p5       ;# n0 → n5
$ns connect $p3 $p4       ;# n2 → n3

# ----------------------------
# Finish Procedure
# ----------------------------
proc finish {} {
    global ns nf tf
    $ns flush-trace
    close $nf
    close $tf
    exec nam lab2.nam &
    exit 0
}

# ----------------------------
# Schedule Ping Events
# ----------------------------
for {set i 1} {$i <= 10} {incr i} {
    set time [expr $i * 0.1]
    $ns at $time "$p1 send"
    $ns at $time "$p3 send"
}

# ----------------------------
# End Simulation
# ----------------------------
$ns at 2.0 "finish"
$ns run
