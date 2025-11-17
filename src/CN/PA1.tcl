# ========================
#      PA1.tcl (NS2)
# ========================

# Create simulator object
set ns [new Simulator]

# Create NAM trace file
set nf [open PA1.nam w]
$ns namtrace-all $nf

# Create trace file (.tr)
set tf [open PA1.tr w]
$ns trace-all $tf

# ------------------------
# Finish Procedure
# ------------------------
proc finish { } {
    global ns nf tf

    $ns flush-trace
    close $nf
    close $tf

    # Open NAM animation
    exec nam PA1.nam &

    exit 0
}

# ------------------------
# Create Nodes
# ------------------------
set n0 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

# ------------------------
# Create Links
# ------------------------
$ns duplex-link $n0 $n2 200Mb 10ms DropTail
$ns duplex-link $n2 $n3 1Mb 1000ms DropTail

# Limit queue size
$ns queue-limit $n0 $n2 10

# ------------------------
# Transport Layer
# ------------------------
set udp0 [new Agent/UDP]
$ns attach-agent $n0 $udp0

# ------------------------
# Application Layer (CBR)
# ------------------------
set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 500
$cbr0 set interval_ 0.005
$cbr0 attach-agent $udp0

# ------------------------
# Create Receiver (Null Agent)
# ------------------------
set null0 [new Agent/Null]
$ns attach-agent $n3 $null0

# Connect sender to receiver
$ns connect $udp0 $null0

# ------------------------
# Events
# ------------------------
$ns at 0.1 "$cbr0 start"
$ns at 1.0 "finish"

# ------------------------
# Run Simulation
# ------------------------
$ns run
